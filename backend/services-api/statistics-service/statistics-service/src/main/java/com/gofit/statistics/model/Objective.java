package com.gofit.statistics.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

public class Objective {

    private Long id;
    private String goalType;
    private Double targetValue;
    private Double currentValue;
    private Timestamp startDate;
    private Timestamp endDate;
    private String status;

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

