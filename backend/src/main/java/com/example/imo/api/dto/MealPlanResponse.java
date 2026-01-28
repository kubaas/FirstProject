package com.example.imo.api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MealPlanResponse {
  private UUID planId;
  private UUID userId;
  private LocalDate weekStart;
  private String strategy;
  private List<MealPlanDayView> days;

  public UUID getPlanId() {
    return planId;
  }

  public void setPlanId(UUID planId) {
    this.planId = planId;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public LocalDate getWeekStart() {
    return weekStart;
  }

  public void setWeekStart(LocalDate weekStart) {
    this.weekStart = weekStart;
  }

  public String getStrategy() {
    return strategy;
  }

  public void setStrategy(String strategy) {
    this.strategy = strategy;
  }

  public List<MealPlanDayView> getDays() {
    return days;
  }

  public void setDays(List<MealPlanDayView> days) {
    this.days = days;
  }
}

