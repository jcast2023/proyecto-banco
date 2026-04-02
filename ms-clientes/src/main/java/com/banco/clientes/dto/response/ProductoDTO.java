package com.banco.clientes.dto.response;

import lombok.Data;

@Data
public class ProductoDTO {
    private String tipo;
    private String nombre;
    private Double saldo;
}
