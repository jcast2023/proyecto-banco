package com.banco.productos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("productos")
public class Producto {

    @Id
    private Long id;

    @Column("codigo_unico")
    private String codigoUnico;

    @Column("nombre_producto")
    private String nombre;

    @Column("tipo_producto")
    private String tipo;

    private double saldo;

    @Column("codigo_cliente")
    private String codigoCliente;

    // Recomendado: Añadir auditoría para evitar errores de "column not found"
    @Column("fecha_creacion")
    private java.time.LocalDateTime fechaCreacion;

    @Column("fecha_actualizacion")
    private java.time.LocalDateTime fechaActualizacion;
}