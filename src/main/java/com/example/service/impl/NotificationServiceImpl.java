package com.example.service.impl;

import com.example.dto.SosEventDto;
import com.example.entity.Sos;
import com.example.entity.User;
import com.example.service.EmailService;
import com.example.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final EmailService emailService;

    @Override
    public void sendSosToContacts(User user, Sos sos) {
        messagingTemplate.convertAndSend("/topic/sos/" + user.getId(),
                new SosEventDto(user.getId(), sos.getLat(), sos.getLng(), "SOS ALERT!"));
    }

    @Override
    public void sendEmergencyEmail(Sos sos) {
        String recipientEmail = sos.getUser().getEmail();
        String subject = "🚨 TƏCİLİ: SOS Eskalasiya";
        String message = "ID: " + sos.getId() + " üçün kömək lazımdır!";
        emailService.sendEmergencyEmail(recipientEmail, subject, message);
    }
}