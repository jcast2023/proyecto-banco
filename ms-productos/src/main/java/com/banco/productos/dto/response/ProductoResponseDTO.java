package com.banco.productos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder; // <-- REVISA QUE SEA ESTE
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private String codigoUnico; // <--- Agregado
    private String tipo;
    private String nombre;
    private double saldo;
}