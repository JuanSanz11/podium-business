package com.tuapp.controller;

import com.tuapp.entity.Deuda;
import com.tuapp.entity.User;
import com.tuapp.repository.DeudaRepository;
import com.tuapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/deudas")
@RequiredArgsConstructor
public class DeudaController {

    private final DeudaRepository deudaRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        return userRepository.findByUsername(username).orElseThrow();
    }

    @GetMapping
    public List<Deuda> getAllDeudas() {
        return deudaRepository.findByUserId(getCurrentUser().getId());
    }

    @PostMapping
    public Deuda createDeuda(@RequestBody Deuda deuda) {
        deuda.setUser(getCurrentUser());
        if (deuda.getMontoPagado() == null) {
            deuda.setMontoPagado(BigDecimal.ZERO);
        }
        return deudaRepository.save(deuda);
    }
}
