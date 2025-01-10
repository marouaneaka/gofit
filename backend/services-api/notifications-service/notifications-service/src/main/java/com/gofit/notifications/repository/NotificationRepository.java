package com.gofit.notifications.repository;

import com.gofit.notifications.model.Notification;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class NotificationRepository {

    @PersistenceContext(unitName = "notificationsPU")
    private EntityManager entityManager;

    public void save(Notification notification) {
        entityManager.persist(notification);
    }

    public Notification findById(Long id) {
        return entityManager.find(Notification.class, id);
    }

    public List<Notification> findAll() {
        return entityManager.createQuery("SELECT n FROM Notification n", Notification.class)
                            .getResultList();
    }

    public void update(Notification notification) {
        entityManager.merge(notification);
    }

    public boolean delete(Long id) {
        Notification notification = entityManager.find(Notification.class, id);
        if (notification != null) {
            entityManager.remove(notification);
        }
                return false;
    }
}