package com.example.imo.service;

import com.example.imo.api.dto.OnboardingRequest;
import com.example.imo.domain.UserProfile;
import com.example.imo.repo.UserProfileRepository;
import com.example.imo.util.CsvUtil;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserOnboardingService {

  public static final String PROCESS_KEY_ONBOARDING = "imo_onboarding";

  private final UserProfileRepository userProfileRepository;
  private final RuntimeService runtimeService;
  public UserOnboardingService(
      UserProfileRepository userProfileRepository,
      RuntimeService runtimeService) {
    this.userProfileRepository = userProfileRepository;
    this.runtimeService = runtimeService;
  }

  @Transactional
  public OnboardingResult createUserAndGenerateInitialPlan(OnboardingRequest req) {
    UUID userId = UUID.randomUUID();
    UserProfile user =
        new UserProfile(
            userId,
            req.getDisplayName(),
            req.getHeightCm(),
            req.getWeightKg(),
            req.getActivityLevel(),
            req.getGoal(),
            req.getMealsPerDay(),
            req.getBudget(),
            req.getCookTimeMinutes(),
            req.getDietStyle(),
            CsvUtil.join(req.getAllergens()),
            Instant.now());
    userProfileRepository.save(user);

    VariableMap vars =
        Variables.createVariables()
            .putValue("userId", userId.toString())
            .putValue("initialStrategy", "BASE");

    ProcessInstanceWithVariables pi =
        runtimeService
            .createProcessInstanceByKey(PROCESS_KEY_ONBOARDING)
            .setVariables(vars)
            .executeWithVariablesInReturn();

    String planIdStr = pi.getVariables().getValue("planId", String.class);
    if (planIdStr == null || planIdStr.isBlank()) {
      throw new IllegalStateException("Onboarding process did not return planId");
    }
    return new OnboardingResult(userId, UUID.fromString(planIdStr));
  }

  public static class OnboardingResult {
    private final UUID userId;
    private final UUID planId;

    public OnboardingResult(UUID userId, UUID planId) {
      this.userId = userId;
      this.planId = planId;
    }

    public UUID getUserId() {
      return userId;
    }

    public UUID getPlanId() {
      return planId;
    }
  }
}
