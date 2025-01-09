package com.gofit.notifications.repository;

import com.gofit.notifications.model.Objective;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ObjectiveRepository {

    @PersistenceContext(unitName = "notificationsPU")
    private EntityManager entityManager;

    public void save(Objective objective) {
        entityManager.persist(objective);
    }

    public Objective findById(Long id) {
        return entityManager.find(Objective.class, id);
    }

    public List<Objective> findAll() {
        return entityManager.createQuery("SELECT a FROM Objective a", Objective.class)
                            .getResultList();
    }

    public void update(Objective objective) {
        entityManager.merge(objective);
    }

    public void delete(Long id) {
        Objective objective = entityManager.find(Objective.class, id);
        if (objective != null) {
            entityManager.remove(objective);
        }
    }
}

