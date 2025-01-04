package com.gofit.services.activity.service;

import com.gofit.services.activity.model.Activity;
import com.gofit.services.activity.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityService {

    private static final String TOPIC = "activity.created";

    @Autowired
    private ActivityRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Activity saveActivity(Activity activity) {
        Activity savedActivity = repository.save(activity);
        kafkaTemplate.send(TOPIC, "New activity created: " + activity.getActivityType());
        return savedActivity;
    }

    public Optional<Activity> updateActivity(Long id, Activity activity) {
        return repository.findById(id).map(existingActivity -> {
            existingActivity.setActivityType(activity.getActivityType());
            existingActivity.setDuration(activity.getDuration());
            existingActivity.setLostCalories(activity.getLostCalories());
            existingActivity.setGainedCalories(activity.getGainedCalories());
            existingActivity.setActivityDate(activity.getActivityDate());
            return repository.save(existingActivity);
        });
    }

    public boolean deleteActivity(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}