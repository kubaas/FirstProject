package com.example.imo.workflow;

import com.example.imo.service.MealPlanService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("generateInitialPlan")
public class GenerateInitialPlanDelegate implements JavaDelegate {

  private final MealPlanService mealPlanService;

  public GenerateInitialPlanDelegate(MealPlanService mealPlanService) {
    this.mealPlanService = mealPlanService;
  }

  @Override
  public void execute(DelegateExecution execution) {
    String userIdStr = (String) execution.getVariable("userId");
    String strategy = (String) execution.getVariable("initialStrategy");
    if (strategy == null || strategy.isBlank()) {
      strategy = "BASE";
    }
    UUID planId = mealPlanService.generatePlan(UUID.fromString(userIdStr), strategy);
    execution.setVariable("planId", planId.toString());
  }
}

