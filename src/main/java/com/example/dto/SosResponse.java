package com.example.dto;

import com.example.sos.enums.SosStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SosResponse {

    private Long id;
    private Long userId;
    private Double lat;
    private Double lng;
    private SosStatus status;
    private LocalDateTime createdAt;


}
