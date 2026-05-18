package com.tuapp.service;

import com.tuapp.dto.FacturaDTO;
import com.tuapp.entity.Factura;
import com.tuapp.entity.User;
import com.tuapp.mapper.FacturaMapper;
import com.tuapp.repository.FacturaRepository;
import com.tuapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final UserRepository userRepository;
    private final FacturaMapper facturaMapper;

    public List<FacturaDTO> findAllByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return facturaRepository.findByUserId(user.getId()).stream()
                .map(facturaMapper::toDto)
                .collect(Collectors.toList());
    }

    public FacturaDTO save(FacturaDTO facturaDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Factura factura = facturaMapper.toEntity(facturaDTO);
        factura.setUser(user);
        return facturaMapper.toDto(facturaRepository.save(factura));
    }

    public FacturaDTO update(Long id, FacturaDTO facturaDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Factura existing = facturaRepository.findById(id).orElseThrow();
        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        existing.setDescripcion(facturaDTO.getDescripcion());
        existing.setMonto(facturaDTO.getMonto());
        existing.setFechaEmision(facturaDTO.getFechaEmision());
        existing.setCategoria(facturaDTO.getCategoria());
        return facturaMapper.toDto(facturaRepository.save(existing));
    }

    public void delete(Long id, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Factura existing = facturaRepository.findById(id).orElseThrow();
        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        facturaRepository.delete(existing);
    }
}
