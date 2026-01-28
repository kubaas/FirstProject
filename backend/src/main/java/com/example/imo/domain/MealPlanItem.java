package com.example.imo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "meal_plan_items")
public class MealPlanItem {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "meal_plan_id", nullable = false, updatable = false)
  private UUID mealPlanId;

  @Column(name = "day_index", nullable = false)
  private int dayIndex; // 0..6

  @Enumerated(EnumType.STRING)
  @Column(name = "meal_type", nullable = false)
  private MealType mealType;

  @Column(name = "recipe_id", nullable = false)
  private UUID recipeId;

  @Column(name = "notes", nullable = false)
  private String notes;

  protected MealPlanItem() {}

  public MealPlanItem(
      UUID id, UUID mealPlanId, int dayIndex, MealType mealType, UUID recipeId, String notes) {
    this.id = id;
    this.mealPlanId = mealPlanId;
    this.dayIndex = dayIndex;
    this.mealType = mealType;
    this.recipeId = recipeId;
    this.notes = notes;
  }

  public UUID getId() {
    return id;
  }

  public UUID getMealPlanId() {
    return mealPlanId;
  }

  public int getDayIndex() {
    return dayIndex;
  }

  public MealType getMealType() {
    return mealType;
  }

  public UUID getRecipeId() {
    return recipeId;
  }

  public String getNotes() {
    return notes;
  }
}

