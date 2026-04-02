package com.banco.clientes.repository;

import com.banco.clientes.entity.Cliente;
import org.springframework.data.r2dbc.repository.R2dbcRepository; // <-- CAMBIO AQUÍ
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClienteRepository extends R2dbcRepository<Cliente, Long> {

    Mono<Cliente> findByCodigoUnico(String codigoUnico);

    Mono<Cliente> findByNumeroDocumento(String numeroDocumento);
}