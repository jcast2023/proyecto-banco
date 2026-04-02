package com.banco.clientes.service;

import com.banco.clientes.dto.request.ClienteRequestDTO;
import com.banco.clientes.dto.response.ClienteResponseDTO;
import reactor.core.publisher.Mono;

/**
 * Interfaz de servicio para operaciones con Clientes.
 *
 * Define el contrato (métodos disponibles) sin especificar cómo se implementan.
 *
 * Mono<T>: Tipo de retorno reactivo (WebFlux).
 *   - Mono = 0 o 1 resultado (como Optional pero asíncrono)
 *   - Flux = 0 o muchos resultados (lista reactiva)
 */
public interface ClienteService {

    /**
     * Busca un cliente por su código único.
     *
     * @param codigoUnico Código único del cliente (ej: CLI-001)
     * @return Mono con el cliente encontrado, o vacío si no existe
     */
    Mono<ClienteResponseDTO> obtenerPorCodigoUnico(String codigoUnico);

    /**
     * Crea un nuevo cliente.
     *
     * @param requestDTO Datos del cliente a crear
     * @return Mono con el cliente creado (incluye ID generado)
     */
    Mono<ClienteResponseDTO> crearCliente(ClienteRequestDTO requestDTO);

    /**
     * Busca un cliente por número de documento.
     *
     * @param numeroDocumento Número de DNI/CE/Pasaporte
     * @return Mono con el cliente encontrado, o vacío si no existe
     */
    Mono<ClienteResponseDTO> obtenerPorNumeroDocumento(String numeroDocumento);

    /**
     * Verifica si existe un cliente con el código único dado.
     *
     * @param codigoUnico Código a verificar
     * @return Mono con true si existe, false si no
     */
    Mono<Boolean> existePorCodigoUnico(String codigoUnico);

    // En ClienteService.java

}