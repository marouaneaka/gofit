package com.gofit.notifications.kafka;

import com.gofit.notifications.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class KafkaProducerService {

    @Inject
    private KafkaConfig kafkaConfig;

    private KafkaProducer<String, String> producer;

    @PostConstruct
    public void init() {
        // Ici, kafkaConfig n'est plus null,
        // car l'injection CDI est déjà faite
        this.producer = new KafkaProducer<>(kafkaConfig.producerProperties());
    }

    public void sendMessage(String topic, String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            } else {
                System.out.println("Message sent to topic: " + metadata.topic()
                                   + ", partition: " + metadata.partition()
                                   + ", offset: " + metadata.offset());
            }
        });
    }
}

