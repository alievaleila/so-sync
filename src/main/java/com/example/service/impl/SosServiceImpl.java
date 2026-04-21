package com.example.service.impl;

import com.example.dto.SosRequestDto;
import com.example.dto.SosResponseDto;
import com.example.entity.Sos;
import com.example.entity.User;
import com.example.enums.SosStatus;
import com.example.mapper.SosMapper;
import com.example.repository.SosRepository;
import com.example.service.SosService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SosServiceImpl implements SosService {

    private final SosRepository sosRepository;
    private final SosMapper sosMapper;

    @Override
    public SosResponseDto sendSos(SosRequestDto request) {
        Sos sos=sosMapper.toEntity(request);

        User user= new User();
        user.setId(1L);

        sos.setUser(user);
        sos.setStatus(SosStatus.PENDING);

        Sos saved=sosRepository.save(sos);

        return sosMapper.toResponse(saved);
    }
}
