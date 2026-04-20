package com.example.service;

import com.example.dto.SosRequest;
import com.example.dto.SosResponse;

public interface SosService {

    SosResponse sendSos(SosRequest request);
}
