package com.example.service;

import com.example.dto.SosRequestDto;
import com.example.dto.SosResponseDto;

public interface SosService {

    SosResponseDto sendSos(SosRequestDto request, String email);

    SosResponseDto resolveSos(Long id, String currentEmail);
}
