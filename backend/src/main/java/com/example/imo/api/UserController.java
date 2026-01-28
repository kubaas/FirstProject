package com.example.imo.api;

import com.example.imo.api.dto.CheckInRequest;
import com.example.imo.api.dto.MealPlanResponse;
import com.example.imo.api.dto.OnboardingRequest;
import com.example.imo.api.dto.OnboardingResponse;
import com.example.imo.service.MealPlanService;
import com.example.imo.service.UserOnboardingService;
import com.example.imo.service.WeeklyCycleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

  private final UserOnboardingService userOnboardingService;
  private final WeeklyCycleService weeklyCycleService;
  private final MealPlanService mealPlanService;

  public UserController(
      UserOnboardingService userOnboardingService,
      WeeklyCycleService weeklyCycleService,
      MealPlanService mealPlanService) {
    this.userOnboardingService = userOnboardingService;
    this.weeklyCycleService = weeklyCycleService;
    this.mealPlanService = mealPlanService;
  }

  @PostMapping("/onboarding")
  public ResponseEntity<OnboardingResponse> onboarding(@Valid @RequestBody OnboardingRequest request) {
    UserOnboardingService.OnboardingResult result =
        userOnboardingService.createUserAndGenerateInitialPlan(request);

    MealPlanResponse plan = mealPlanService.getPlan(result.getPlanId());

    OnboardingResponse resp = new OnboardingResponse();
    resp.setUserId(result.getUserId());
    resp.setMealPlan(plan);
    return ResponseEntity.ok(resp);
  }

  @GetMapping("/{userId}/plans/latest")
  public ResponseEntity<MealPlanResponse> latestPlan(@PathVariable UUID userId) {
    return mealPlanService
        .findLatestPlan(userId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/{userId}/checkins")
  public ResponseEntity<MealPlanResponse> checkIn(
      @PathVariable UUID userId, @Valid @RequestBody CheckInRequest request) {
    UUID planId = weeklyCycleService.submitCheckInAndGenerateNewPlan(userId, request);
    return ResponseEntity.ok(mealPlanService.getPlan(planId));
  }
}

