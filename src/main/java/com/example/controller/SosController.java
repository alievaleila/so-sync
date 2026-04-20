package com.example.controller;

import com.example.dto.SosRequest;
import com.example.dto.SosResponse;
import com.example.service.SosService;
import com.example.service.SosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sos")
public class SosController {

    private final SosService sosService;

    @PostMapping("/send")
    public ResponseEntity<SosResponse> sendSos(@Valid @RequestBody SosRequest request) {

        SosResponse response = sosService.sendSos(request);

        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(response);
    }
}
