package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SosResponseDto {

    private Long id;
    private BigDecimal lat;
    private BigDecimal lng;
    private String status;
    private LocalDateTime createdAt;


}
