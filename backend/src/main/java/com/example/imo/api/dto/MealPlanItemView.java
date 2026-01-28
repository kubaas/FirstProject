package com.example.imo.api.dto;

import com.example.imo.domain.MealType;

public class MealPlanItemView {
  private MealType mealType;
  private RecipeView recipe;
  private String notes;

  public MealType getMealType() {
    return mealType;
  }

  public void setMealType(MealType mealType) {
    this.mealType = mealType;
  }

  public RecipeView getRecipe() {
    return recipe;
  }

  public void setRecipe(RecipeView recipe) {
    this.recipe = recipe;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}

