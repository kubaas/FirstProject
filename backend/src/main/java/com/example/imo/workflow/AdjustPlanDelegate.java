package com.example.imo.workflow;

import com.example.imo.service.MealPlanService;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("adjustPlanFromCheckIn")
public class AdjustPlanDelegate implements JavaDelegate {

  private final DecisionService decisionService;
  private final MealPlanService mealPlanService;

  public AdjustPlanDelegate(DecisionService decisionService, MealPlanService mealPlanService) {
    this.decisionService = decisionService;
    this.mealPlanService = mealPlanService;
  }

  @Override
  public void execute(DelegateExecution execution) {
    String userIdStr = (String) execution.getVariable("userId");

    double complianceScore = getDouble(execution, "complianceScore");
    double hungerEvening = getDouble(execution, "hungerEvening");
    double energyAfterMeals = getDouble(execution, "energyAfterMeals");
    double sleepScore = getDouble(execution, "sleepScore");
    double cravings = getDouble(execution, "cravings");

    VariableMap inputs =
        Variables.createVariables()
            .putValue("complianceScore", complianceScore)
            .putValue("hungerEvening", hungerEvening)
            .putValue("energyAfterMeals", energyAfterMeals)
            .putValue("sleepScore", sleepScore)
            .putValue("cravings", cravings);

    DmnDecisionTableResult result =
        decisionService.evaluateDecisionTableByKey("plan_adjustments", inputs);

    String strategy = "BASE";
    if (!result.isEmpty()) {
      String s = result.getSingleResult().getEntry("strategy");
      if (s != null && !s.isBlank()) {
        strategy = s;
      }
    }

    UUID planId = mealPlanService.generatePlan(UUID.fromString(userIdStr), strategy);
    execution.setVariable("planId", planId.toString());
    execution.setVariable("strategy", strategy);
  }

  private double getDouble(DelegateExecution execution, String name) {
    Object v = execution.getVariable(name);
    if (v instanceof Number) {
      return ((Number) v).doubleValue();
    }
    if (v instanceof String) {
      return Double.parseDouble((String) v);
    }
    return 0.0;
  }
}

