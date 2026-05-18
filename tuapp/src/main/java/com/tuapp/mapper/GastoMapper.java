package com.tuapp.mapper;

import com.tuapp.dto.GastoDTO;
import com.tuapp.entity.Gasto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GastoMapper {
    GastoDTO toDto(Gasto gasto);

    @Mapping(target = "user", ignore = true)
    Gasto toEntity(GastoDTO gastoDTO);
}
