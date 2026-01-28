package com.example.imo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "meal_plans")
public class MealPlan {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "user_id", nullable = false, updatable = false)
  private UUID userId;

  @Column(name = "week_start", nullable = false)
  private LocalDate weekStart;

  @Column(name = "strategy", nullable = false)
  private String strategy;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  protected MealPlan() {}

  public MealPlan(UUID id, UUID userId, LocalDate weekStart, String strategy, Instant createdAt) {
    this.id = id;
    this.userId = userId;
    this.weekStart = weekStart;
    this.strategy = strategy;
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public LocalDate getWeekStart() {
    return weekStart;
  }

  public String getStrategy() {
    return strategy;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}

