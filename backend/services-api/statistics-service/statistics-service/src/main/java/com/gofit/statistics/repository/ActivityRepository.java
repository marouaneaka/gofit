package com.gofit.statistics.repository;

import com.gofit.statistics.model.Activity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class ActivityRepository {
    @PersistenceContext(unitName = "activitiesPU")
    private EntityManager entityManager;

    public List<Activity> findAll() {
        return entityManager.createQuery("SELECT a FROM Activity a", Activity.class).getResultList();
    }
}
