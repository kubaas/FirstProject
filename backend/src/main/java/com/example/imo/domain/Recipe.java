package com.example.imo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "recipes")
public class Recipe {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "suggested_meal_type", nullable = false)
  private MealType suggestedMealType;

  @Column(name = "prep_minutes", nullable = false)
  private int prepMinutes;

  @Column(name = "tags_csv", nullable = false)
  private String tagsCsv;

  @Column(name = "calories", nullable = false)
  private int calories;

  @Column(name = "protein_g", nullable = false)
  private double proteinG;

  @Column(name = "carbs_g", nullable = false)
  private double carbsG;

  @Column(name = "fat_g", nullable = false)
  private double fatG;

  protected Recipe() {}

  public Recipe(
      UUID id,
      String name,
      MealType suggestedMealType,
      int prepMinutes,
      String tagsCsv,
      int calories,
      double proteinG,
      double carbsG,
      double fatG) {
    this.id = id;
    this.name = name;
    this.suggestedMealType = suggestedMealType;
    this.prepMinutes = prepMinutes;
    this.tagsCsv = tagsCsv;
    this.calories = calories;
    this.proteinG = proteinG;
    this.carbsG = carbsG;
    this.fatG = fatG;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public MealType getSuggestedMealType() {
    return suggestedMealType;
  }

  public int getPrepMinutes() {
    return prepMinutes;
  }

  public String getTagsCsv() {
    return tagsCsv;
  }

  public int getCalories() {
    return calories;
  }

  public double getProteinG() {
    return proteinG;
  }

  public double getCarbsG() {
    return carbsG;
  }

  public double getFatG() {
    return fatG;
  }
}

