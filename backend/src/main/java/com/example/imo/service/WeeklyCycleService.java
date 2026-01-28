package com.example.imo.service;

import com.example.imo.api.dto.CheckInRequest;
import com.example.imo.domain.WeeklyCheckIn;
import com.example.imo.repo.WeeklyCheckInRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class WeeklyCycleService {

  public static final String PROCESS_KEY_WEEKLY = "imo_weekly_cycle";

  private final WeeklyCheckInRepository weeklyCheckInRepository;
  private final RuntimeService runtimeService;

  public WeeklyCycleService(WeeklyCheckInRepository weeklyCheckInRepository, RuntimeService runtimeService) {
    this.weeklyCheckInRepository = weeklyCheckInRepository;
    this.runtimeService = runtimeService;
  }

  @Transactional
  public UUID submitCheckInAndGenerateNewPlan(UUID userId, CheckInRequest req) {
    WeeklyCheckIn saved =
        new WeeklyCheckIn(
            UUID.randomUUID(),
            userId,
            req.getComplianceScore(),
            req.getHungerEvening(),
            req.getEnergyAfterMeals(),
            req.getSleepScore(),
            req.getCravings(),
            Instant.now());
    weeklyCheckInRepository.save(saved);

    VariableMap vars =
        Variables.createVariables()
            .putValue("userId", userId.toString())
            .putValue("complianceScore", req.getComplianceScore())
            .putValue("hungerEvening", req.getHungerEvening())
            .putValue("energyAfterMeals", req.getEnergyAfterMeals())
            .putValue("sleepScore", req.getSleepScore())
            .putValue("cravings", req.getCravings());

    ProcessInstanceWithVariables pi =
        runtimeService
            .createProcessInstanceByKey(PROCESS_KEY_WEEKLY)
            .setVariables(vars)
            .executeWithVariablesInReturn();

    String planIdStr = pi.getVariables().getValue("planId", String.class);
    if (planIdStr == null || planIdStr.isBlank()) {
      throw new IllegalStateException("Weekly cycle process did not return planId");
    }
    return UUID.fromString(planIdStr);
  }
}

