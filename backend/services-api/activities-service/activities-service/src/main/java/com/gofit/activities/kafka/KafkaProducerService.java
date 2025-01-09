package com.gofit.activities.kafka;

import com.gofit.activities.config.KafkaConfig;
import com.gofit.shared.ActivityEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;

@ApplicationScoped
public class KafkaProducerService {

    @Inject
    private KafkaConfig kafkaConfig;

    private KafkaProducer<String, String> producer;
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        // Initialisation du KafkaProducer avec les propriétés configurées
        this.producer = new KafkaProducer<>(kafkaConfig.producerProperties());
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Envoie un ActivityEvent sérialisé en JSON à Kafka.
     *
     * @param topic Le nom du topic Kafka.
     * @param event L'objet ActivityEvent à envoyer.
     */
    public void sendActivityEvent(String topic, ActivityEvent event) {
        try {
            // Sérialiser l'objet ActivityEvent en JSON
            String serializedEvent = objectMapper.writeValueAsString(event);

            // Création et envoi du message Kafka
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, event.getType(), serializedEvent);
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    exception.printStackTrace();
                } else {
                    System.out.println("ActivityEvent envoyé au topic: " + metadata.topic()
                                       + ", partition: " + metadata.partition()
                                       + ", offset: " + metadata.offset());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la sérialisation de ActivityEvent: " + e.getMessage());
        }
    }

    @PreDestroy
    public void shutdown() {
        if (producer != null) {
            producer.close();
            System.out.println("KafkaProducerService arrêté.");
        }
    }
}
