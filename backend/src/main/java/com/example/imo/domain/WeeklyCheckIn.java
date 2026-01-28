package com.example.imo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "weekly_checkins")
public class WeeklyCheckIn {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "user_id", nullable = false, updatable = false)
  private UUID userId;

  @Column(name = "compliance_score", nullable = false)
  private double complianceScore; // 0..1

  @Column(name = "hunger_evening", nullable = false)
  private double hungerEvening; // 0..1

  @Column(name = "energy_after_meals", nullable = false)
  private double energyAfterMeals; // 0..1

  @Column(name = "sleep_score", nullable = false)
  private double sleepScore; // 0..1

  @Column(name = "cravings", nullable = false)
  private double cravings; // 0..1

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  protected WeeklyCheckIn() {}

  public WeeklyCheckIn(
      UUID id,
      UUID userId,
      double complianceScore,
      double hungerEvening,
      double energyAfterMeals,
      double sleepScore,
      double cravings,
      Instant createdAt) {
    this.id = id;
    this.userId = userId;
    this.complianceScore = complianceScore;
    this.hungerEvening = hungerEvening;
    this.energyAfterMeals = energyAfterMeals;
    this.sleepScore = sleepScore;
    this.cravings = cravings;
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public double getComplianceScore() {
    return complianceScore;
  }

  public double getHungerEvening() {
    return hungerEvening;
  }

  public double getEnergyAfterMeals() {
    return energyAfterMeals;
  }

  public double getSleepScore() {
    return sleepScore;
  }

  public double getCravings() {
    return cravings;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}

