package com.gofit.shared;

import java.io.Serializable;

public class GoalAchievedEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String goalType;
    private String goalDescription;

    public GoalAchievedEvent() {
    }

    public GoalAchievedEvent(Long userId, String goalType, String goalDescription) {
        this.userId = userId;
        this.goalType = goalType;
        this.goalDescription = goalDescription;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    @Override
    public String toString() {
        return "GoalAchievedEvent{" +
                "userId=" + userId +
                ", goalType='" + goalType + '\'' +
                ", goalDescription='" + goalDescription + '\'' +
                '}';
    }
}