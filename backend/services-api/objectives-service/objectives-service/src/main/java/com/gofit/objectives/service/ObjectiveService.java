package com.gofit.objectives.service;

import com.gofit.objectives.model.Objective;
import com.gofit.objectives.repository.ObjectiveRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class ObjectiveService {

    @Inject
    private ObjectiveRepository repository;

    public void createObjective(Objective objective) {
        repository.save(objective);
    }

    public Objective getObjective(Long id) {
        return repository.findById(id);
    }

    public List<Objective> getAllObjectives() {
        return repository.findAll();
    }
}

