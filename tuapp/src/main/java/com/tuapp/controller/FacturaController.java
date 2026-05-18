package com.tuapp.controller;

import com.tuapp.dto.FacturaDTO;
import com.tuapp.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAll(Authentication authentication) {
        return ResponseEntity.ok(facturaService.findAllByUser(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<FacturaDTO> create(@RequestBody FacturaDTO facturaDTO, Authentication authentication) {
        return ResponseEntity.ok(facturaService.save(facturaDTO, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaDTO> update(@PathVariable Long id, @RequestBody FacturaDTO facturaDTO, Authentication authentication) {
        return ResponseEntity.ok(facturaService.update(id, facturaDTO, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        facturaService.delete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
