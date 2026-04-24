package com.example.controller;

import com.example.dto.SosRequestDto;
import com.example.dto.SosResponseDto;
import com.example.service.SosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sos")
public class SosController {

    private final SosService sosService;

    @PostMapping
    public ResponseEntity<SosResponseDto> sendSos(
            @Valid @RequestBody SosRequestDto request,
            Authentication authentication) {

        String email = authentication.getName();

        SosResponseDto response = sosService.sendSos(request, email);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<SosResponseDto> resolve(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(sosService.resolveSos(id, authentication.getName()));
    }
}
