package com.tuapp.controller;

import com.tuapp.dto.GastoDTO;
import com.tuapp.dto.ReporteDTO;
import com.tuapp.service.GastoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos")
@RequiredArgsConstructor
public class GastoController {

    private final GastoService gastoService;

    @GetMapping
    public ResponseEntity<List<GastoDTO>> getAll(Authentication authentication) {
        return ResponseEntity.ok(gastoService.findAllByUser(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<GastoDTO> create(@RequestBody GastoDTO gastoDTO, Authentication authentication) {
        return ResponseEntity.ok(gastoService.save(gastoDTO, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GastoDTO> update(@PathVariable Long id, @RequestBody GastoDTO gastoDTO, Authentication authentication) {
        return ResponseEntity.ok(gastoService.update(id, gastoDTO, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        gastoService.delete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reporte")
    public ResponseEntity<ReporteDTO> getReporte(Authentication authentication) {
        return ResponseEntity.ok(gastoService.getReporte(authentication.getName()));
    }
}
