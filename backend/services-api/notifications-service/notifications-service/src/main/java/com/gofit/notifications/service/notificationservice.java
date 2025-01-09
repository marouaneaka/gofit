package com.gofit.notifications.service;

import com.gofit.notifications.model.Objective;
import com.gofit.notifications.repository.ObjectiveRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class notificationservice {

    @Inject
    private ObjectiveRepository repository;

    public void createObjective(Objective objective) {
        repository.save(objective);
    }

    public Objective getObjective(Long id) {
        return repository.findById(id);
    }

    public List<Objective> getAllnotifications() {
        return repository.findAll();
    }
}

