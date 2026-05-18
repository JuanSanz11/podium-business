package com.tuapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {
    private BigDecimal totalIngresos; // Si facturas = ingresos
    private BigDecimal totalGastos;
    private BigDecimal totalAhorro;
    private Map<String, BigDecimal> gastosPorCategoria;
}
