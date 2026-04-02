package com.banco.clientes.mapper;

import com.banco.clientes.dto.request.ClienteRequestDTO;
import com.banco.clientes.dto.response.ClienteResponseDTO;
import com.banco.clientes.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponseDTO toResponseDTO(Cliente entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    Cliente toEntity(ClienteRequestDTO requestDTO);
}