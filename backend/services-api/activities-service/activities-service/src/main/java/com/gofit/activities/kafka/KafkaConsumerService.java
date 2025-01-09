package com.gofit.activities.kafka;

import com.gofit.activities.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.gofit.activities.repository.ActivityRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedExecutorService;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

@ApplicationScoped
public class KafkaConsumerService {

    @Inject
    private KafkaConfig kafkaConfig;

    @Inject
    private ActivityRepository repository; // si besoin

    @Resource(name = "DefaultManagedExecutorService")
    ManagedExecutorService executor;

    private AtomicBoolean running = new AtomicBoolean(false);
    private KafkaConsumer<String, String> consumer;

    @PostConstruct
    public void init() {
        consumer = new KafkaConsumer<>(kafkaConfig.consumerProperties());
        consumer.subscribe(Collections.singletonList("activities-events"));

        running.set(true);
        executor.execute(() -> {
            while (running.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    // Appel EJB/JPA (ex: repository.save(...)) possible ici
                    System.out.println("Consumed: " + record.value());
                }
            }
        });
    }

    @PreDestroy
    public void shutdown() {
        running.set(false);
        consumer.wakeup();  // Interrompt le poll bloquant
        consumer.close();
        System.out.println("KafkaConsumerService stopped.");
    }
}

