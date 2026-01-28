package com.example.imo.api.dto;

import java.util.List;

public class MealPlanDayView {
  private int dayIndex;
  private List<MealPlanItemView> items;

  public int getDayIndex() {
    return dayIndex;
  }

  public void setDayIndex(int dayIndex) {
    this.dayIndex = dayIndex;
  }

  public List<MealPlanItemView> getItems() {
    return items;
  }

  public void setItems(List<MealPlanItemView> items) {
    this.items = items;
  }
}

