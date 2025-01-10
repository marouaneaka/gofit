package com.gofit.notifications.kafka;

import com.gofit.notifications.config.KafkaConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofit.notifications.config.KafkaConfig;
import com.gofit.notifications.model.Notification;
import com.gofit.notifications.repository.NotificationRepository;
import com.gofit.shared.GoalAchievedEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.Properties;

@Singleton
@Startup
public class KafkaConsumerService {

    @PersistenceContext(unitName = "notificationsPU")
    private EntityManager entityManager;

    private KafkaConsumer<String, String> consumer;
    private ObjectMapper objectMapper;

    @Inject
    private NotificationRepository notificationRepository;

    @PostConstruct
    public void init() {
        KafkaConfig kafkaConfig = new KafkaConfig();
        consumer = new KafkaConsumer<>(kafkaConfig.consumerProperties());
        consumer.subscribe(Collections.singletonList("goal.achieved"));
        objectMapper = new ObjectMapper();
    }

    @Schedule(hour = "*", minute = "*", second = "*/20", persistent = false)
    @Transactional
    public void pollKafka() {
        try {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(20000));
            if (records.isEmpty()) {
                System.out.println("Aucun message Kafka, on réessaiera plus tard...");
                return;
            }

            for (ConsumerRecord<String, String> record : records) {
                processEvent(record.value());
            }

            consumer.commitAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processEvent(String eventJson) {
        try {
            GoalAchievedEvent event = objectMapper.readValue(eventJson, GoalAchievedEvent.class);
            Notification notification = new Notification();
            notification.setNotificationType("goal.achieved");
            notification.setMessage("Félicitations! Vous avez atteint votre objectif: " + event.getGoalType());
            notification.setTimestamp(new Timestamp(System.currentTimeMillis()));
 
            notificationRepository.save(notification);

            // Envoyer la notification au frontend (par exemple via WebSocket ou autre mécanisme)
            sendNotificationToFront(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotificationToFront(Notification notification) {
        // Implémentation pour envoyer la notification au frontend
    }

    @PreDestroy
    public void cleanup() {
        if (consumer != null) {
            consumer.close();
        }
    }
}