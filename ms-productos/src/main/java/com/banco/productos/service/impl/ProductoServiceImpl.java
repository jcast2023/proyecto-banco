package com.banco.productos.service.impl;

import com.banco.productos.dto.response.ProductoResponseDTO;
import com.banco.productos.mapper.ProductoMapper;
import com.banco.productos.repository.ProductoRepository;
import com.banco.productos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    @Override
    public Flux<ProductoResponseDTO> listarPorCodigoUnico(String codigoCliente) {
        // CAMBIO: Ahora usamos el método que busca por el dueño de los productos
        return repository.findByCodigoCliente(codigoCliente)
                .map(mapper::toDto);
    }
}