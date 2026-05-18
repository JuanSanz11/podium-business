package com.tuapp.repository;

import com.tuapp.entity.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByUserId(Long userId);
    List<Gasto> findByUserIdAndCategoria(Long userId, String categoria);
}
