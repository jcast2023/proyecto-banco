package com.banco.bff.service;

import com.banco.bff.dto.response.ClienteCompletoDTO;
import reactor.core.publisher.Mono;

public interface IBffService {
    // Definimos el contrato
    Mono<ClienteCompletoDTO> obtenerDetalleIntegral(String codigoUnicoEncrypted);
}