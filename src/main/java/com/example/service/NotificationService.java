package com.example.service;

import com.example.entity.Sos;
import com.example.entity.User;

public interface NotificationService {
    void sendSosToContacts(User user, Sos sos);

    void sendEmergencyEmail(Sos sos);
}