package com.example.repository;

import com.example.entity.Sos;
import com.example.enums.SosStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SosRepository extends JpaRepository<Sos, Long> {
    List<Sos> findAllByStatus(SosStatus sosStatus);
}
