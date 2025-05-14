package com.psy.services;

import com.psy.models.Notification;
import com.psy.repositories.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public Notification createOrUpdateNotification(Notification notification) {
        return repository.save(notification);
    }

    public void deleteNotification(Integer id) {
        repository.deleteById(id);
    }

    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    public Notification getNotificationById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Notification Not Found")
        );
    }

    public void sendNotification(Notification notification) {
        simpMessagingTemplate.convertAndSend("/topic/psy", notification);
    }
}
