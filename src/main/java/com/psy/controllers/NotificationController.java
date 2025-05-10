package com.psy.controllers;

import com.psy.models.Notification;
import com.psy.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping("/send-notification")
    public ResponseEntity<Void> sendNotification(
            @RequestBody Notification notification
    ) {
        service.sendNotification(notification);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotification() {
        return ResponseEntity.ok(service.getAllNotifications());
    }

    @GetMapping("{id}")
    public ResponseEntity<Notification> getNotificationById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(service.getNotificationById(id));
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @RequestBody Notification notification
    ) {
        return ResponseEntity.ok(service.createOrUpdateNotification(notification));
    }

    @PutMapping
    public ResponseEntity<Notification> updateNotification(
            @RequestBody Notification notification
    ) {
        return ResponseEntity.ok(service.createOrUpdateNotification(notification));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Integer id
    ) {
        service.deleteNotification(id);
        return ResponseEntity.accepted().build();
    }
}
