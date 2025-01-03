package gofit.services.activity.service;

import gofit.services.activity.model.Activity;
import gofit.services.activity.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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
}
