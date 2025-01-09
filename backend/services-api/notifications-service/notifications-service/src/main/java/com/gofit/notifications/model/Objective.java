package com.gofit.notifications.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "notifications")
public class Objective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goal_type", nullable = false, length=50)
    private String goalType;

    @Column(name = "target_value", nullable = false)
    private Double targetValue;

    @Column(name = "current_value", nullable = false)
    private Double currentValue;

    @Column(name = "start_date", nullable = false, updatable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = true)
    private Timestamp endDate;

    @Column(name = "status", nullable = false, length=20)
    private String status;
    
    @PrePersist
    protected void onCreate() {
        this.startDate = Timestamp.from(Instant.now());
        this.status = "IN_PROGRESS";
        this.currentValue = 0.0 ;

    }

    // Getters and Setters

    public Long getId() { return id; }

    public String getGoalType() { return goalType; }

    public Double getTargetValue() { return targetValue; }

    public Double getCurrentValue() { return currentValue; }

    public Timestamp getStartDate() { return startDate; }

    public Timestamp getEndDate() { return endDate; }

    public String getStatus() { return status; }

    public void setId(Long newId) { this.id = newId; }

    public void setGoalType(String newGoalType) { this.goalType = newGoalType; }

    public void setTargetValue(Double newTargetValue) { this.targetValue = newTargetValue; }

    public void setCurrentValue(Double newCurrentValue) { this.currentValue = newCurrentValue; }

    public void setStartDate(Timestamp newStartDate) { this.startDate = newStartDate; }

    public void setEndDate(Timestamp newEndDate) { this.endDate = newEndDate; }

    public void setStatus(String newStatus) { this.status = newStatus; }
}

