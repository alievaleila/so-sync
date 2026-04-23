package com.example.service;

import com.example.entity.Sos;
import com.example.enums.SosStatus;
import com.example.repository.SosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EscalationService {

    private final SosRepository sosRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailService emailService;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void checkAndEscalateSos() {
        log.info("SOS eskalasiya yoxlanışı başladı...");

        List<Sos> pendingSosList = sosRepository.findAllByStatus(SosStatus.PENDING);

        for (Sos sos : pendingSosList) {
            String redisKey = "sos_session:" + sos.getId();

            Boolean hasSession = redisTemplate.hasKey(redisKey);

            if (Boolean.FALSE.equals(hasSession)) {
                log.warn("⚠️ SOS ID {} üçün vaxt bitdi! Eskalasiya edilir...", sos.getId());

                sos.setStatus(SosStatus.ESCALATED);
                sosRepository.save(sos);

                sendFallbackEmail(sos);
            }
        }
    }

    private void sendFallbackEmail(Sos sos) {
        String recipientEmail = sos.getUser().getEmail();
        String subject = "🚨 TƏCİLİ: SOS Eskalasiya Bildirişi";
        String message = String.format(
                "Salam, \n\nID: %d nömrəli SOS siqnalına 3 dəqiqə ərzində cavab verilmədiyi üçün sistem avtomatik eskalasiya etdi.\n" +
                        "Koordinatlar: Lat: %f, Lng: %f",
                sos.getId(), sos.getLat(), sos.getLng()
        );

        try {
            emailService.sendEmergencyEmail(recipientEmail, subject, message);
            log.info("✅ Eskalasiya emaili göndərildi: {}", recipientEmail);
        } catch (Exception e) {
            log.error("❌ Email göndərilərkən xəta baş verdi: {}", e.getMessage());
        }
    }
}