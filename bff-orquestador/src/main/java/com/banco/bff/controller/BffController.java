package com.banco.bff.controller;

import com.banco.bff.dto.response.ClienteCompletoDTO;
import com.banco.bff.service.IBffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bff/cliente-integral")
@RequiredArgsConstructor
public class BffController {

    private final IBffService bffService;

    // Se agrega ("codigoUnico") para solucionar el error de Spring Boot 3.2+
    @GetMapping("/{codigoUnico}")
    public Mono<ClienteCompletoDTO> obtenerDetalle(@PathVariable("codigoUnico") String codigoUnico) {
        return bffService.obtenerDetalleIntegral(codigoUnico);
    }
}