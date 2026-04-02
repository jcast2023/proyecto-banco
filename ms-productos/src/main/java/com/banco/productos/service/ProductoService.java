package com.banco.productos.service;

import com.banco.productos.dto.response.ProductoResponseDTO;
import reactor.core.publisher.Flux;

public interface ProductoService {
    Flux<ProductoResponseDTO> listarPorCodigoUnico(String codigoUnico);
}
