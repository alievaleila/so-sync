package com.example.service.impl;

import com.example.dto.SosRequestDto;
import com.example.dto.SosResponseDto;
import com.example.entity.Sos;
import com.example.entity.User;
import com.example.enums.SosStatus;
import com.example.mapper.SosMapper;
import com.example.repository.SosRepository;
import com.example.repository.UserRepository;
import com.example.service.NotificationService;
import com.example.service.SosService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class SosServiceImpl implements SosService {

    private final SosRepository sosRepository;
    private final SosMapper sosMapper;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate messagingTemplate;

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public SosResponseDto sendSos(SosRequestDto request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Sos sos = sosMapper.toEntity(request);
        sos.setUser(user);
        sos.setStatus(SosStatus.PENDING);
        Sos saved = sosRepository.save(sos);

        notificationService.sendSosToContacts(user, saved);

        redisTemplate.opsForValue().set("sos_session:" + saved.getId(), "PENDING", Duration.ofMinutes(3));

        return sosMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public SosResponseDto resolveSos(Long sosId, String currentEmail) {
        Sos sos = findSosOrThrow(sosId);

        if (!sos.getUser().getEmail().equals(currentEmail)) {
            throw new RuntimeException("Siz bu SOS-u bağlaya bilməzsiniz!");
        }

        sos.setStatus(SosStatus.RESOLVED);
        redisTemplate.delete("sos_session:" + sosId);
        return sosMapper.toResponse(sos);
    }

    private Sos findSosOrThrow(Long id) {
        return sosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SOS with ID " + id + " not found"));
    }

    private void updateSosStatus(Sos sos, SosStatus status) {
        sos.setStatus(status);
        sosRepository.save(sos);
    }

    private void cancelRedisSession(Long id) {
        String redisKey = "sos_session:" + id;
        redisTemplate.delete(redisKey);
    }
}