package com.gofit.statistics.repository;

import com.gofit.statistics.model.Statistics;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class StatisticsRepository {

    @PersistenceContext(unitName = "statisticsPU")
    private EntityManager entityManager;

    public void save(Statistics statistics) {
        entityManager.persist(statistics);
    }

    public List<Statistics> findAll() {
        return entityManager.createQuery("SELECT s FROM Statistics s", Statistics.class).getResultList();
    }

    public Statistics findById(Long id) {
        return entityManager.find(Statistics.class, id);
    }
}
