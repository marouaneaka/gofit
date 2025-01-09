package com.gofit.activities.model;

import java.time.Instant;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private double duration;
    private double distance; 
    private double calories;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getDuration() { return duration; }
    public void setDuration(double duration) { this.duration = duration; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }
    public Timestamp getCreatedAt() { return createdAt; }
}

