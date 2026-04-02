package com.banco.clientes.controller;

import com.banco.clientes.dto.request.ClienteRequestDTO;
import com.banco.clientes.dto.response.ClienteResponseDTO;
import com.banco.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controller REST para operaciones con Clientes.
 *
 * @RestController: Indica que esta clase maneja peticiones HTTP y devuelve JSON.
 * @RequestMapping("/api/clientes): URL base para todos los endpoints de esta clase.
 * @RequiredArgsConstructor: Inyección de dependencias por constructor (Lombok).
 * @Slf4j: Logger para registrar eventos.
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * GET /api/clientes/codigo/{codigoUnico}
     *
     * Busca un cliente por su código único.
     *
     * @PathVariable: Extrae el valor de la URL (lo que va entre { })
     * ResponseEntity: Envuelve la respuesta con código HTTP y headers
     */
    @GetMapping("/codigo/{codigoUnico}")
    public Mono<ClienteResponseDTO> obtenerPorCodigo(
            @PathVariable("codigoUnico") String codigoUnico,
            @RequestHeader(value = "X-Tracking-Id", required = false) String trackingId) {
        log.info("[MS-CLIENTES] Procesando solicitud - Código: {}, Tracking-Id: {}", codigoUnico, trackingId);
        return clienteService.obtenerPorCodigoUnico(codigoUnico);
    }

    /**
     * GET /api/clientes/documento/{numeroDocumento}
     *
     * Busca un cliente por número de documento.
     */
    @GetMapping("/documento/{numeroDocumento}")
    public Mono<ResponseEntity<ClienteResponseDTO>> obtenerPorDocumento(
            @PathVariable String numeroDocumento) {

        log.info("Recibida petición GET para documento: {}", numeroDocumento);

        return clienteService.obtenerPorNumeroDocumento(numeroDocumento)
                .map(ResponseEntity::ok);
    }

    /**
     * POST /api/clientes
     *
     * Crea un nuevo cliente.
     *
     * @RequestBody: Convierte el JSON del body a un objeto Java
     * @ResponseStatus(HttpStatus.CREATED): Devuelve código 201 (creado)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClienteResponseDTO> crearCliente(
            @RequestBody ClienteRequestDTO requestDTO) {

        log.info("Recibida petición POST para crear cliente: {}", requestDTO.getCodigoUnico());

        return clienteService.crearCliente(requestDTO)
                .doOnSuccess(response -> log.info("Cliente creado: {}", response.getId()));
    }

    /**
     * GET /api/clientes/existe/{codigoUnico}
     *
     * Verifica si existe un cliente (útil para validaciones).
     */
    @GetMapping("/existe/{codigoUnico}")
    public Mono<ResponseEntity<Boolean>> verificarExistencia(
            @PathVariable String codigoUnico) {

        log.info("Verificando existencia de: {}", codigoUnico);

        return clienteService.existePorCodigoUnico(codigoUnico)
                .map(ResponseEntity::ok);
    }


}