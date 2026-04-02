package com.banco.clientes.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table(name = "clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    private Long id;

    @Column("codigo_unico")
    private String codigoUnico;

    @Column("nombres")
    private String nombres;

    @Column("apellidos")
    private String apellidos;

    @Column("tipo_documento")
    private String tipoDocumento;

    @Column("numero_documento")
    private String numeroDocumento;

    @CreatedDate
    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}