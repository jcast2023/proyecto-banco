package com.banco.bff.service.impl;

import com.banco.bff.dto.response.ClienteCompletoDTO;
import com.banco.bff.service.IBffService;
import com.banco.bff.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BffServiceImpl implements IBffService {

    private final WebClient.Builder webClientBuilder;

    @Value("${clientes.service.url:http://localhost:8081}")
    private String clientesServiceUrl;

    @Value("${productos.service.url:http://localhost:8082}")
    private String productosServiceUrl;

    @Override
    public Mono<ClienteCompletoDTO> obtenerDetalleIntegral(String codigoUnicoEncrypted) {
        // 1. DESENCRIPTAR (Requerimiento: El codigoUnico debe estar encriptado)
        String codigoUnico = EncryptionUtil.decrypt(codigoUnicoEncrypted);
        // 2. GENERAR TRACKING ID (Requerimiento: Identificador para tracking)
        String trackingId = UUID.randomUUID().toString();

        log.info("[BFF] === INICIO ORQUESTACIÓN === Tracking: {} | Código: {}", trackingId, codigoUnico);

        return ReactiveSecurityContextHolder.getContext()
                .filter(c -> c.getAuthentication() != null && c.getAuthentication().isAuthenticated())
                .map(securityContext -> {
                    Object principal = securityContext.getAuthentication().getPrincipal();
                    if (principal instanceof Jwt) {
                        return ((Jwt) principal).getTokenValue();
                    }
                    throw new RuntimeException("No se encontró un token JWT válido");
                })
                .flatMap(token -> {
                    WebClient webClient = webClientBuilder.build();

                    // Llamada a ms-clientes con Resiliencia
                    Mono<Map<String, Object>> clienteMono = webClient.get()
                            .uri(clientesServiceUrl + "/api/clientes/codigo/{codigoUnico}", codigoUnico)
                            .headers(h -> {
                                h.setBearerAuth(token); // Propagar Seguridad
                                h.add("X-Tracking-Id", trackingId); // Propagar Tracking
                            })
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                            .doOnSuccess(c -> log.info("[BFF] ms-clientes respondió con éxito"))
                            // 🔥 EVITA EL ERROR 500: Si falla clientes, devolvemos un mapa vacío en vez de error
                            .onErrorResume(e -> {
                                log.error("[BFF] Fallo crítico en ms-clientes: {}", e.getMessage());
                                return Mono.just(Map.of("nombres", "No disponible", "apellidos", "Error en servicio"));
                            });

                    // Llamada a ms-productos con Resiliencia
                    Mono<List<Map<String, Object>>> productosMono = webClient.get()
                            .uri(productosServiceUrl + "/api/productos/cliente/{codigoUnico}", codigoUnico)
                            .headers(h -> {
                                h.setBearerAuth(token);
                                h.add("X-Tracking-Id", trackingId);
                            })
                            .retrieve()
                            .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                            .collectList()
                            .doOnSuccess(p -> log.info("[BFF] ms-productos respondió con {} productos", p.size()))
                            // 🔥 EVITA EL ERROR 500: Si falla productos, devolvemos lista vacía
                            .onErrorResume(e -> {
                                log.error("[BFF] Fallo crítico en ms-productos: {}", e.getMessage());
                                return Mono.just(List.of());
                            });

                    return Mono.zip(clienteMono, productosMono)
                            .map(tuple -> {
                                Map<String, Object> cliente = tuple.getT1();
                                List<Map<String, Object>> productos = tuple.getT2();

                                return ClienteCompletoDTO.builder()
                                        .nombres(cliente.get("nombres") != null ? cliente.get("nombres").toString() : "Sin nombre")
                                        .apellidos(cliente.get("apellidos") != null ? cliente.get("apellidos").toString() : "Sin apellido")
                                        .tipoDocumento(cliente.get("tipoDocumento") != null ? cliente.get("tipoDocumento").toString() : "")
                                        .numeroDocumento(cliente.get("numeroDocumento") != null ? cliente.get("numeroDocumento").toString() : "")
                                        .productos(productos)
                                        .build();
                            })
                            .doOnSuccess(dto -> log.info("[BFF] Objeto final construido con éxito"))
                            .doOnError(e -> log.error("[BFF] Error construyendo el DTO final: {}", e.getMessage()));
                });
    }
}