package com.tuapp.mapper;

import com.tuapp.dto.PedidoDTO;
import com.tuapp.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    PedidoDTO toDto(Pedido pedido);

    @Mapping(target = "user", ignore = true)
    Pedido toEntity(PedidoDTO pedidoDTO);
}
