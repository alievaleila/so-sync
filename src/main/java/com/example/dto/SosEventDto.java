package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SosEventDto {

    private Long userId;
    private BigDecimal lat;
    private BigDecimal lng;
    private String message;
}
