package com.gofit.statistics.service;

import com.gofit.statistics.client.ActivitiesClient;
import com.gofit.statistics.client.ObjectivesClient;
import com.gofit.statistics.model.Activity;
import com.gofit.statistics.model.Objective;
import com.gofit.statistics.model.Statistics;
import com.gofit.statistics.repository.StatisticsRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class StatisticsService {

    @Inject
    private ActivitiesClient activitiesClient;

    @Inject
    private ObjectivesClient objectivesClient;

    @Inject
    private StatisticsRepository statisticsRepository;

    public void calculateAndStoreStatistics() {
        // Récupérer les activités et les objectifs
        List<Activity> activities = activitiesClient.getActivities();
        List<Objective> objectives = objectivesClient.getObjectives();

        // Calculer les statistiques
        double totalDistance = activities.stream().mapToDouble(Activity::getDistance).sum();
        double totalCalories = activities.stream().mapToDouble(Activity::getCalories).sum();
        double totalDuration = activities.stream().mapToDouble(Activity::getDuration).sum();

        // Créer un objet Statistics pour sauvegarder les résultats
        Statistics statistics = new Statistics();
        statistics.setTotalDistance(totalDistance);
        statistics.setTotalCalories(totalCalories);
        statistics.setTotalDuration(totalDuration);

        // Sauvegarder les statistiques dans la base de données
        statisticsRepository.save(statistics);

        System.out.println("Statistics saved: " + statistics);
    }

    public List<Statistics> getAllStatistics() {
        return statisticsRepository.findAll();
    }

    public Statistics getStatisticById(Long id) {
        return statisticsRepository.findById(id);
    }
}
