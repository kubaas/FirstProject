package com.example.imo.api.dto;

import com.example.imo.domain.ActivityLevel;
import com.example.imo.domain.Budget;
import com.example.imo.domain.DietStyle;
import com.example.imo.domain.Goal;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class OnboardingRequest {

  @NotBlank
  private String displayName;

  @Min(120)
  @Max(230)
  private int heightCm;

  @Min(30)
  @Max(250)
  private double weightKg;

  @NotNull
  private ActivityLevel activityLevel;

  @NotNull
  private Goal goal;

  @Min(3)
  @Max(3)
  private int mealsPerDay;

  @NotNull
  private Budget budget;

  @Min(5)
  @Max(120)
  private int cookTimeMinutes;

  @NotNull
  private DietStyle dietStyle;

  @NotNull
  private List<String> allergens;

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public int getHeightCm() {
    return heightCm;
  }

  public void setHeightCm(int heightCm) {
    this.heightCm = heightCm;
  }

  public double getWeightKg() {
    return weightKg;
  }

  public void setWeightKg(double weightKg) {
    this.weightKg = weightKg;
  }

  public ActivityLevel getActivityLevel() {
    return activityLevel;
  }

  public void setActivityLevel(ActivityLevel activityLevel) {
    this.activityLevel = activityLevel;
  }

  public Goal getGoal() {
    return goal;
  }

  public void setGoal(Goal goal) {
    this.goal = goal;
  }

  public int getMealsPerDay() {
    return mealsPerDay;
  }

  public void setMealsPerDay(int mealsPerDay) {
    this.mealsPerDay = mealsPerDay;
  }

  public Budget getBudget() {
    return budget;
  }

  public void setBudget(Budget budget) {
    this.budget = budget;
  }

  public int getCookTimeMinutes() {
    return cookTimeMinutes;
  }

  public void setCookTimeMinutes(int cookTimeMinutes) {
    this.cookTimeMinutes = cookTimeMinutes;
  }

  public DietStyle getDietStyle() {
    return dietStyle;
  }

  public void setDietStyle(DietStyle dietStyle) {
    this.dietStyle = dietStyle;
  }

  public List<String> getAllergens() {
    return allergens;
  }

  public void setAllergens(List<String> allergens) {
    this.allergens = allergens;
  }
}

