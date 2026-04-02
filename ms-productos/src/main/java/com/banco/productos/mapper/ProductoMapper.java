package com.banco.productos.mapper;

import com.banco.productos.dto.response.ProductoResponseDTO;
import com.banco.productos.entity.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    // Si los nombres son idénticos, MapStruct lo hace automático.
    // Si llegaran a ser diferentes (ej: tipoProducto vs tipo), usarías @Mapping
    ProductoResponseDTO toDto(Producto entity);
}