package com.tuapp.service;

import com.tuapp.dto.GastoDTO;
import com.tuapp.dto.ReporteDTO;
import com.tuapp.entity.Factura;
import com.tuapp.entity.Gasto;
import com.tuapp.entity.User;
import com.tuapp.mapper.GastoMapper;
import com.tuapp.repository.FacturaRepository;
import com.tuapp.repository.GastoRepository;
import com.tuapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GastoService {

    private final GastoRepository gastoRepository;
    private final FacturaRepository facturaRepository;
    private final UserRepository userRepository;
    private final GastoMapper gastoMapper;

    public List<GastoDTO> findAllByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return gastoRepository.findByUserId(user.getId()).stream()
                .map(gastoMapper::toDto)
                .collect(Collectors.toList());
    }

    public GastoDTO save(GastoDTO gastoDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Gasto gasto = gastoMapper.toEntity(gastoDTO);
        gasto.setUser(user);
        return gastoMapper.toDto(gastoRepository.save(gasto));
    }

    public GastoDTO update(Long id, GastoDTO gastoDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Gasto existing = gastoRepository.findById(id).orElseThrow();
        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        existing.setDescripcion(gastoDTO.getDescripcion());
        existing.setMonto(gastoDTO.getMonto());
        existing.setFecha(gastoDTO.getFecha());
        existing.setCategoria(gastoDTO.getCategoria());
        existing.setAhorroOInversion(gastoDTO.isAhorroOInversion());
        return gastoMapper.toDto(gastoRepository.save(existing));
    }

    public void delete(Long id, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Gasto existing = gastoRepository.findById(id).orElseThrow();
        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        gastoRepository.delete(existing);
    }

    public ReporteDTO getReporte(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Factura> facturas = facturaRepository.findByUserId(user.getId());
        List<Gasto> gastos = gastoRepository.findByUserId(user.getId());

        BigDecimal totalIngresos = facturas.stream()
                .map(Factura::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalGastos = gastos.stream()
                .filter(g -> !g.isAhorroOInversion())
                .map(Gasto::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAhorro = gastos.stream()
                .filter(Gasto::isAhorroOInversion)
                .map(Gasto::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> gastosPorCategoria = gastos.stream()
                .filter(g -> !g.isAhorroOInversion())
                .collect(Collectors.groupingBy(
                        g -> g.getCategoria() != null ? g.getCategoria() : "Otros",
                        Collectors.reducing(BigDecimal.ZERO, Gasto::getMonto, BigDecimal::add)
                ));

        return ReporteDTO.builder()
                .totalIngresos(totalIngresos)
                .totalGastos(totalGastos)
                .totalAhorro(totalAhorro)
                .gastosPorCategoria(gastosPorCategoria)
                .build();
    }
}
