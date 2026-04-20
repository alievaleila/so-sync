package com.example.repository;

import com.example.entity.Sos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SosRepository extends JpaRepository<Sos, Long> {
}
