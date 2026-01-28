package com.example.imo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserProfile {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "display_name", nullable = false)
  private String displayName;

  @Column(name = "height_cm", nullable = false)
  private int heightCm;

  @Column(name = "weight_kg", nullable = false)
  private double weightKg;

  @Enumerated(EnumType.STRING)
  @Column(name = "activity_level", nullable = false)
  private ActivityLevel activityLevel;

  @Enumerated(EnumType.STRING)
  @Column(name = "goal", nullable = false)
  private Goal goal;

  @Column(name = "meals_per_day", nullable = false)
  private int mealsPerDay;

  @Enumerated(EnumType.STRING)
  @Column(name = "budget", nullable = false)
  private Budget budget;

  @Column(name = "cook_time_minutes", nullable = false)
  private int cookTimeMinutes;

  @Enumerated(EnumType.STRING)
  @Column(name = "diet_style", nullable = false)
  private DietStyle dietStyle;

  @Column(name = "allergens_csv", nullable = false)
  private String allergensCsv;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  protected UserProfile() {}

  public UserProfile(
      UUID id,
      String displayName,
      int heightCm,
      double weightKg,
      ActivityLevel activityLevel,
      Goal goal,
      int mealsPerDay,
      Budget budget,
      int cookTimeMinutes,
      DietStyle dietStyle,
      String allergensCsv,
      Instant createdAt) {
    this.id = id;
    this.displayName = displayName;
    this.heightCm = heightCm;
    this.weightKg = weightKg;
    this.activityLevel = activityLevel;
    this.goal = goal;
    this.mealsPerDay = mealsPerDay;
    this.budget = budget;
    this.cookTimeMinutes = cookTimeMinutes;
    this.dietStyle = dietStyle;
    this.allergensCsv = allergensCsv;
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public int getHeightCm() {
    return heightCm;
  }

  public double getWeightKg() {
    return weightKg;
  }

  public ActivityLevel getActivityLevel() {
    return activityLevel;
  }

  public Goal getGoal() {
    return goal;
  }

  public int getMealsPerDay() {
    return mealsPerDay;
  }

  public Budget getBudget() {
    return budget;
  }

  public int getCookTimeMinutes() {
    return cookTimeMinutes;
  }

  public DietStyle getDietStyle() {
    return dietStyle;
  }

  public String getAllergensCsv() {
    return allergensCsv;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}

