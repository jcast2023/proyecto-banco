package com.banco.clientes.service.impl;

import com.banco.clientes.dto.request.ClienteRequestDTO;
import com.banco.clientes.dto.response.ClienteResponseDTO;
import com.banco.clientes.dto.response.ProductoDTO;
import com.banco.clientes.entity.Cliente;
import com.banco.clientes.mapper.ClienteMapper;
import com.banco.clientes.repository.ClienteRepository;
import com.banco.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<ClienteResponseDTO> obtenerPorCodigoUnico(String codigoUnico) {
        return clienteRepository.findByCodigoUnico(codigoUnico)
                .switchIfEmpty(Mono.error(new RuntimeException("No encontrado")))
                .map(clienteMapper::toResponseDTO);
    }

    @Override
    public Mono<ClienteResponseDTO> crearCliente(ClienteRequestDTO requestDTO) {
        log.info("Creando cliente con código: {}", requestDTO.getCodigoUnico());

        // 1. Convertimos DTO a Entidad
        Cliente entity = clienteMapper.toEntity(requestDTO);

        // 2. Guardamos (Ahora .save() será reconocido por el IDE)
        return clienteRepository.save(entity)
                .map(clienteMapper::toResponseDTO)
                .doOnSuccess(cliente -> log.info("Cliente creado exitosamente"));
    }

    @Override
    public Mono<ClienteResponseDTO> obtenerPorNumeroDocumento(String numeroDocumento) {
        return clienteRepository.findByNumeroDocumento(numeroDocumento)
                .switchIfEmpty(Mono.error(new RuntimeException("No encontrado")))
                .map(clienteMapper::toResponseDTO);
    }

    @Override
    public Mono<Boolean> existePorCodigoUnico(String codigoUnico) {
        return clienteRepository.findByCodigoUnico(codigoUnico)
                .map(c -> true)
                .defaultIfEmpty(false);
    }

    // Cambia la firma para recibir el trackingId real
    public Flux<ProductoDTO> obtenerProductosDelCliente(String codigoUnico, String trackingId) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/productos/cliente/{codigo}", codigoUnico)
                .header("X-Tracking-Id", trackingId) // Usamos el ID que viene del BFF
                .retrieve()
                .bodyToFlux(ProductoDTO.class);
    }


}