package com.gofit.notifications.service;

import com.gofit.notifications.model.Notification;
import com.gofit.notifications.repository.NotificationRepository;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class NotificationService {

    @Inject
    private NotificationRepository notificationRepository;

    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public boolean deleteNotification(Long id) {
        return notificationRepository.delete(id);
    }
}