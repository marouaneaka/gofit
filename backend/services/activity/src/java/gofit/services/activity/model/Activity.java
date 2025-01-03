package gofit.services.activity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activityType;
    private Float duration;
    private Float lostCalories;
    private Float gainedCalories;
    private LocalDateTime activityDate;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Float getLostCalories() {
        return lostCalories;
    }

    public void setLostCalories(Float lostCalories) {
        this.lostCalories = lostCalories;
    }

    public Float getGainedCalories() {
        return gainedCalories;
    }

    public void setGainedCalories(Float gainedCalories) {
        this.gainedCalories = gainedCalories;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }
}