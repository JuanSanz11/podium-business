package com.tuapp.controller;

import com.tuapp.dto.PedidoDTO;
import com.tuapp.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAll(Authentication authentication) {
        return ResponseEntity.ok(pedidoService.findAllByUser(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> create(@RequestBody PedidoDTO pedidoDTO, Authentication authentication) {
        return ResponseEntity.ok(pedidoService.registrarPedido(pedidoDTO, authentication.getName()));
    }
}
