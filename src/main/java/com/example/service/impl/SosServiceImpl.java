package com.example.service.impl;

import com.example.dto.SosRequest;
import com.example.dto.SosResponse;
import com.example.entity.Sos;
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

    private static final String SOS_KEY_PREFIX = "sos:";

    private final SosRepository sosRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SosMapper sosMapper;

    @Override
    public SosResponse sendSos(SosRequest request) {

        Sos sos = sosMapper.toEntity(request);
        sos.setStatus(SosStatus.PENDING);
        sos.setCreatedAt(LocalDateTime.now());

        Sos saved = sosRepository.save(sos);

        String key = SOS_KEY_PREFIX + saved.getUserId();

        redisTemplate.opsForValue().set(key, "ACTIVE", Duration.ofMinutes(3));

        return sosMapper.toResponse(saved);
    }
}
