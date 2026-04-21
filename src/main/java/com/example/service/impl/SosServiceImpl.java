package com.example.service.impl;

import com.example.dto.SosRequestDto;
import com.example.dto.SosResponseDto;
import com.example.entity.Sos;
import com.example.entity.User;
import com.example.enums.SosStatus;
import com.example.mapper.SosMapper;
import com.example.repository.SosRepository;
import com.example.repository.UserRepository;
import com.example.service.SosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SosServiceImpl implements SosService {

    private final SosRepository sosRepository;
    private final SosMapper sosMapper;
    private final UserRepository userRepository;

    @Override
    public SosResponseDto sendSos(SosRequestDto request, String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Sos sos = sosMapper.toEntity(request);

        sos.setUser(user);
        sos.setStatus(SosStatus.PENDING);

        Sos saved = sosRepository.save(sos);

        return sosMapper.toResponse(saved);
    }
}
