package com.example.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SosRequestDto {

    @DecimalMin(value = "-90", message = "Latitude must be greater than or equal to -90")
    @DecimalMax(value = "90", message = "Latitude must be less than or equal to 90")
    @NotNull(message = "Latitude must not be null")
    private BigDecimal lat;

    @DecimalMin(value = "-180", message = "Longitude must be greater than or equal to -180")
    @DecimalMax(value = "180", message = "Longitude must be less than or equal to 180")
    @NotNull(message = "Longitude must not be null")
    private BigDecimal lng;
}
