package com.example.imo.api.dto;

import java.util.UUID;

public class OnboardingResponse {
  private UUID userId;
  private MealPlanResponse mealPlan;

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public MealPlanResponse getMealPlan() {
    return mealPlan;
  }

  public void setMealPlan(MealPlanResponse mealPlan) {
    this.mealPlan = mealPlan;
  }
}

