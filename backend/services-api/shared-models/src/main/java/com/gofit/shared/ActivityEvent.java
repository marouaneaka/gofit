package com.gofit.shared;

import java.io.Serializable;

public class ActivityEvent implements Serializable {
    private String type;
    private double distance;
    private double duration;
    private double calories;

    public ActivityEvent() {}

    public ActivityEvent(String type, double distance, double duration, double calories) {
        this.type = type;
        this.distance = distance;
        this.duration = duration;
        this.calories = calories;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "ActivityEvent{" +
                "type='" + type + '\'' +
                ", distance=" + distance +
                ", duration=" + duration +
                ", calories=" + calories +
                '}';
    }
}

