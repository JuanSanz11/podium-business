package com.tuapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Long id;
    private String producto;
    private Integer cantidad;
    private String estado;
    private LocalDateTime fechaCreacion;
}
