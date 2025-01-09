package com.gofit.activities.repository;

import com.gofit.activities.model.Activity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ActivityRepository {

    @PersistenceContext(unitName = "activitiesPU")
    private EntityManager entityManager;

    public void save(Activity activity) {
        entityManager.persist(activity);
    }

    public Activity findById(Long id) {
        return entityManager.find(Activity.class, id);
    }

    public List<Activity> findAll() {
        return entityManager.createQuery("SELECT a FROM Activity a", Activity.class)
                            .getResultList();
    }

    public boolean delete(Long id) {
        Activity activity = entityManager.find(Activity.class, id);
        if (activity != null) {
            entityManager.remove(activity);
            return true;
        }
        return false;
    }
}

