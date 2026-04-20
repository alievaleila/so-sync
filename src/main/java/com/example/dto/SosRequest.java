package com.example.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SosRequest {

    @NotNull
    private Long userId;

    @DecimalMin("-90")
    @DecimalMax("90")
    @NotNull
    private Double lat;

    @DecimalMin("-180")
    @DecimalMax("180")
    @NotNull
    private Double lng;
}
