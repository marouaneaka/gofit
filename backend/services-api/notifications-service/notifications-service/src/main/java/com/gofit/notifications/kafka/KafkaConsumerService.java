package com.gofit.notifications.kafka;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofit.notifications.config.KafkaConfig;
import com.gofit.notifications.model.Objective;
import com.gofit.shared.ActivityEvent;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import java.sql.Timestamp;
import java.time.Instant;

@Singleton
@Startup
public class KafkaConsumerService {

    @PersistenceContext(unitName = "notificationsPU")
    private EntityManager entityManager;

    private KafkaConsumer<String, String> consumer;
    private ObjectMapper objectMapper;

    /**
     * Initialisation du consumer Kafka (appelée au démarrage du conteneur).
     */
    @PostConstruct
    public void init() {
        // Configuration du KafkaConsumer
        KafkaConfig kafkaConfig = new KafkaConfig();
        consumer = new KafkaConsumer<>(kafkaConfig.consumerProperties());
        // On s’abonne au topic
        consumer.subscribe(Collections.singletonList("activities-events"));

        // Instancier l’ObjectMapper (pour désérialiser les JSON)
        objectMapper = new ObjectMapper();
    }

    /**
     * Méthode planifiée, exécutée par GlassFish toutes les 20 secondes.
     * - L’EntityManager est accessible (thread conteneur).
     * - On récupère et on traite les messages Kafka.
     */
    @Schedule(hour = "*", minute = "*", second = "*/20", persistent = false)
    @Transactional
    public void pollKafka() {
        try {
            // On attend jusqu’à 20 secondes pour obtenir des messages
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(20000));
            
            if (records.isEmpty()) {
                System.out.println("Aucun message Kafka, on réessaiera plus tard...");
                return;
            }

            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Received event: Key=" + record.key() + ", Value=" + record.value());
                processEvent(record.value());
            }

            // Optionnel: valider l’offset automatiquement
            consumer.commitAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Traiter un message JSON (désérialisation + mise à jour des objectifs).
     */
    private void processEvent(String eventJson) {
        try {
            System.out.println("Processing event JSON: " + eventJson);
            ActivityEvent event = objectMapper.readValue(eventJson, ActivityEvent.class);
            System.out.println("Deserialized event: " + event);

            updatenotifications("distance", event.getDistance());
            updatenotifications("duration", event.getDuration());
            updatenotifications("calories", event.getCalories());
        } catch (Exception e) {
            System.err.println("Error processing event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mettre à jour les objectifs correspondants (colonne `goalType` = distance/duration/calories, etc.).
     */
    private void updatenotifications(String goalType, double valueToAdd) {
        if (valueToAdd <= 0) return;

        // Chargement des objectifs correspondants
        List<Objective> notifications = entityManager.createQuery(
                "SELECT o FROM Objective o WHERE o.goalType = :goalType AND o.status = :status", Objective.class)
                .setParameter("goalType", goalType)
                .setParameter("status", "IN_PROGRESS")
                .getResultList();

        // Mise à jour de la valeur courante
        for (Objective objective : notifications) {
            objective.setCurrentValue(objective.getCurrentValue() + valueToAdd);

            // Si la valeur courante atteint ou dépasse la cible, on marque l'objectif comme terminé
            if (objective.getCurrentValue() >= objective.getTargetValue()) {
                objective.setStatus("COMPLETED");
                objective.setEndDate(Timestamp.from(Instant.now())); // Mise à jour de la date de fin
            }
            
            entityManager.merge(objective);
        }
    }

    /**
     * Fermeture propre du consumer lorsque le conteneur se termine ou redéploie.
     */
    @PreDestroy
    public void cleanup() {
        if (consumer != null) {
            consumer.close();
        }
    }
}
