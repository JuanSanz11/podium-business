package com.tuapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTO {
    private Long id;
    private String descripcion;
    private BigDecimal monto;
    private LocalDate fechaEmision;
    private String categoria;
}
