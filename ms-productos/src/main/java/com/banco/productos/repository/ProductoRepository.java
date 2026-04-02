package com.banco.productos.repository;

import com.banco.productos.entity.Producto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {
    // CAMBIO CRÍTICO: Buscar por el código del dueño (cliente), no por el ID del producto
    Flux<Producto> findByCodigoCliente(String codigoCliente);
}