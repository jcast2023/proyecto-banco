package com.banco.bff.controller;

import com.banco.bff.dto.response.ClienteCompletoDTO;
import com.banco.bff.service.IBffService;
import org.junit.jupiter.api.Test; // Importante: usar org.junit.jupiter (JUnit 5)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@WebFluxTest(BffController.class) // Solo carga la capa web del BFF
class BffControllerTest {

    @Autowired
    private WebTestClient webTestClient; // Cliente para simular peticiones HTTP

    @MockBean
    private IBffService bffService; // Simulamos el servicio para no llamar a los microservicios reales

    @Test
    void obtenerDetalle_DebeRetornarClienteConProductos_CuandoCodigoEsValido() {
        // 1. PREPARAR
        ClienteCompletoDTO mockResponse = new ClienteCompletoDTO();
        mockResponse.setNombres("Julio");
        mockResponse.setApellidos("Castillo");
        mockResponse.setTipoDocumento("DNI");
        mockResponse.setNumeroDocumento("77777777");

        // 2. SIMULAR (Ajustado al nombre real: obtenerDetalleIntegral y 1 solo parámetro)
        when(bffService.obtenerDetalleIntegral(anyString()))
                .thenReturn(Mono.just(mockResponse));

        // 3. EJECUTAR Y VERIFICAR
        webTestClient.mutateWith(mockJwt()) // <--- ESTO SIMULA EL TOKEN
                .get()
                .uri("/api/bff/cliente-integral/Q0xJLTAwMQ==")
                .header("X-Tracking-Id", "7d424d59-8156-41af-ad20-4bfac0187e4d")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombres").isEqualTo("Julio")
                .jsonPath("$.numeroDocumento").isEqualTo("77777777");
    }
}