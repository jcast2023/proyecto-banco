package com.banco.productos.controller;

import com.banco.productos.dto.response.ProductoResponseDTO;
import com.banco.productos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Slf4j // Para logback
public class ProductoController {

    private final ProductoService service;

    @GetMapping("/cliente/{codigoUnico}")
    public Flux<ProductoResponseDTO> getProductos(
            @PathVariable("codigoUnico") String codigoUnico,
            @RequestHeader(value = "X-Tracking-Id", required = false) String trackingId) {
        log.info("[MS-PRODUCTOS] Consultando productos - Cliente: {}, Tracking-Id: {}", codigoUnico, trackingId);
        return service.listarPorCodigoUnico(codigoUnico);
    }
}