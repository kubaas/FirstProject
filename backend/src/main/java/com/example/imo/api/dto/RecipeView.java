package com.example.imo.api.dto;

import com.example.imo.domain.MealType;
import java.util.List;
import java.util.UUID;

public class RecipeView {
  private UUID id;
  private String name;
  private MealType suggestedMealType;
  private int prepMinutes;
  private List<String> tags;
  private int calories;
  private double proteinG;
  private double carbsG;
  private double fatG;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MealType getSuggestedMealType() {
    return suggestedMealType;
  }

  public void setSuggestedMealType(MealType suggestedMealType) {
    this.suggestedMealType = suggestedMealType;
  }

  public int getPrepMinutes() {
    return prepMinutes;
  }

  public void setPrepMinutes(int prepMinutes) {
    this.prepMinutes = prepMinutes;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public int getCalories() {
    return calories;
  }

  public void setCalories(int calories) {
    this.calories = calories;
  }

  public double getProteinG() {
    return proteinG;
  }

  public void setProteinG(double proteinG) {
    this.proteinG = proteinG;
  }

  public double getCarbsG() {
    return carbsG;
  }

  public void setCarbsG(double carbsG) {
    this.carbsG = carbsG;
  }

  public double getFatG() {
    return fatG;
  }

  public void setFatG(double fatG) {
    this.fatG = fatG;
  }
}

