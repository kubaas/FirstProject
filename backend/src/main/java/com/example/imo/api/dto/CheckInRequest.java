package com.example.imo.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class CheckInRequest {

  @Min(0)
  @Max(1)
  private double complianceScore;

  @Min(0)
  @Max(1)
  private double hungerEvening;

  @Min(0)
  @Max(1)
  private double energyAfterMeals;

  @Min(0)
  @Max(1)
  private double sleepScore;

  @Min(0)
  @Max(1)
  private double cravings;

  public double getComplianceScore() {
    return complianceScore;
  }

  public void setComplianceScore(double complianceScore) {
    this.complianceScore = complianceScore;
  }

  public double getHungerEvening() {
    return hungerEvening;
  }

  public void setHungerEvening(double hungerEvening) {
    this.hungerEvening = hungerEvening;
  }

  public double getEnergyAfterMeals() {
    return energyAfterMeals;
  }

  public void setEnergyAfterMeals(double energyAfterMeals) {
    this.energyAfterMeals = energyAfterMeals;
  }

  public double getSleepScore() {
    return sleepScore;
  }

  public void setSleepScore(double sleepScore) {
    this.sleepScore = sleepScore;
  }

  public double getCravings() {
    return cravings;
  }

  public void setCravings(double cravings) {
    this.cravings = cravings;
  }
}

