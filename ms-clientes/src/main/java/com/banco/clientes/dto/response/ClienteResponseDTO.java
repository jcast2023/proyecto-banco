package com.banco.clientes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {

    private Long id;


    private String codigoUnico;


    private String nombres;


    private String apellidos;


    private String tipoDocumento;


    private String numeroDocumento;


    private LocalDateTime fechaCreacion;


    private LocalDateTime fechaActualizacion;
}