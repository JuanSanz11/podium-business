package com.tuapp.mapper;

import com.tuapp.dto.FacturaDTO;
import com.tuapp.entity.Factura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacturaMapper {
    FacturaDTO toDto(Factura factura);

    @Mapping(target = "user", ignore = true)
    Factura toEntity(FacturaDTO facturaDTO);
}
