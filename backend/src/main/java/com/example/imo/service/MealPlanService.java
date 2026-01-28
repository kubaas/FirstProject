package com.example.imo.service;

import com.example.imo.api.dto.MealPlanDayView;
import com.example.imo.api.dto.MealPlanItemView;
import com.example.imo.api.dto.MealPlanResponse;
import com.example.imo.api.dto.RecipeView;
import com.example.imo.domain.DietStyle;
import com.example.imo.domain.MealPlan;
import com.example.imo.domain.MealPlanItem;
import com.example.imo.domain.MealType;
import com.example.imo.domain.Recipe;
import com.example.imo.domain.UserProfile;
import com.example.imo.repo.MealPlanItemRepository;
import com.example.imo.repo.MealPlanRepository;
import com.example.imo.repo.RecipeRepository;
import com.example.imo.repo.UserProfileRepository;
import com.example.imo.util.CsvUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MealPlanService {

  private final UserProfileRepository userProfileRepository;
  private final RecipeRepository recipeRepository;
  private final MealPlanRepository mealPlanRepository;
  private final MealPlanItemRepository mealPlanItemRepository;

  public MealPlanService(
      UserProfileRepository userProfileRepository,
      RecipeRepository recipeRepository,
      MealPlanRepository mealPlanRepository,
      MealPlanItemRepository mealPlanItemRepository) {
    this.userProfileRepository = userProfileRepository;
    this.recipeRepository = recipeRepository;
    this.mealPlanRepository = mealPlanRepository;
    this.mealPlanItemRepository = mealPlanItemRepository;
  }

  @Transactional
  public UUID generatePlan(UUID userId, String strategy) {
    UserProfile user =
        userProfileRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

    LocalDate weekStart = LocalDate.now();
    UUID planId = UUID.randomUUID();
    MealPlan plan = new MealPlan(planId, userId, weekStart, strategy, Instant.now());
    mealPlanRepository.save(plan);

    Map<MealType, List<Recipe>> pool = buildRecipePool(user, strategy);

    Random rnd = new Random(planId.getMostSignificantBits() ^ planId.getLeastSignificantBits());
    List<MealPlanItem> items = new ArrayList<>();

    // MVP: 3 meals/day (BREAKFAST/LUNCH/DINNER)
    List<MealType> types = List.of(MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER);
    for (int day = 0; day < 7; day++) {
      for (MealType type : types) {
        List<Recipe> candidates = pool.getOrDefault(type, List.of());
        if (candidates.isEmpty()) {
          throw new IllegalStateException("No recipes for meal type " + type);
        }
        Recipe chosen = candidates.get(rnd.nextInt(candidates.size()));
        String notes = buildNotes(strategy, type);
        items.add(new MealPlanItem(UUID.randomUUID(), planId, day, type, chosen.getId(), notes));
      }
    }

    mealPlanItemRepository.saveAll(items);
    return planId;
  }

  public Optional<MealPlanResponse> findLatestPlan(UUID userId) {
    return mealPlanRepository
        .findTopByUserIdOrderByCreatedAtDesc(userId)
        .map(plan -> toResponse(plan, mealPlanItemRepository.findByMealPlanIdOrderByDayIndexAsc(plan.getId())));
  }

  public MealPlanResponse getPlan(UUID planId) {
    MealPlan plan =
        mealPlanRepository
            .findById(planId)
            .orElseThrow(() -> new IllegalArgumentException("Meal plan not found: " + planId));
    List<MealPlanItem> items = mealPlanItemRepository.findByMealPlanIdOrderByDayIndexAsc(planId);
    return toResponse(plan, items);
  }

  private Map<MealType, List<Recipe>> buildRecipePool(UserProfile user, String strategy) {
    List<Recipe> all = recipeRepository.findAll();
    boolean vegetarian = user.getDietStyle() == DietStyle.VEGETARIAN;

    List<String> allergens = CsvUtil.split(user.getAllergensCsv()).stream().map(String::toLowerCase).collect(Collectors.toList());

    List<Recipe> filtered =
        all.stream()
            .filter(r -> r.getPrepMinutes() <= user.getCookTimeMinutes())
            .filter(
                r -> {
                  List<String> tags = CsvUtil.split(r.getTagsCsv()).stream().map(String::toLowerCase).collect(Collectors.toList());
                  if (vegetarian && !tags.contains("vegetarian")) {
                    return false;
                  }
                  for (String a : allergens) {
                    if (!a.isEmpty() && tags.contains(a)) {
                      return false;
                    }
                  }
                  return true;
                })
            .collect(Collectors.toList());

    // Strategy tweaks: prefer "quick" or "high_protein"
    Comparator<Recipe> boost =
        (a, b) -> Integer.compare(scoreRecipe(b, strategy), scoreRecipe(a, strategy));

    Map<MealType, List<Recipe>> byType = new EnumMap<>(MealType.class);
    for (MealType t : MealType.values()) {
      List<Recipe> list =
          filtered.stream()
              .filter(r -> r.getSuggestedMealType() == t)
              .sorted(boost)
              .collect(Collectors.toList());
      byType.put(t, list);
    }
    return byType;
  }

  private int scoreRecipe(Recipe r, String strategy) {
    List<String> tags = CsvUtil.split(r.getTagsCsv()).stream().map(String::toLowerCase).collect(Collectors.toList());
    int score = 0;
    if (tags.contains("imo")) score += 5;

    if ("QUICK".equalsIgnoreCase(strategy) && tags.contains("quick")) score += 3;
    if ("HIGH_PROTEIN".equalsIgnoreCase(strategy) && tags.contains("high_protein")) score += 3;
    if ("EVENING_HUNGER".equalsIgnoreCase(strategy) && tags.contains("high_protein")) score += 2;
    if ("EVENING_HUNGER".equalsIgnoreCase(strategy) && tags.contains("low_carb")) score += 1;

    return score;
  }

  private String buildNotes(String strategy, MealType type) {
    if ("EVENING_HUNGER".equalsIgnoreCase(strategy) && type == MealType.DINNER) {
      return "Cel tygodnia: stabilna kolacja białkowa (mniej podjadania wieczorem).";
    }
    if ("QUICK".equalsIgnoreCase(strategy)) {
      return "Cel tygodnia: minimalny czas gotowania, trzymamy prostotę.";
    }
    return "";
  }

  private MealPlanResponse toResponse(MealPlan plan, List<MealPlanItem> items) {
    Map<UUID, Recipe> recipes = new HashMap<>();
    recipeRepository.findAllById(items.stream().map(MealPlanItem::getRecipeId).collect(Collectors.toSet()))
        .forEach(r -> recipes.put(r.getId(), r));

    Map<Integer, List<MealPlanItem>> byDay =
        items.stream().collect(Collectors.groupingBy(MealPlanItem::getDayIndex));

    List<MealPlanDayView> days = new ArrayList<>();
    for (int day = 0; day < 7; day++) {
      List<MealPlanItem> dayItems =
          byDay.getOrDefault(day, List.of()).stream()
              .sorted(Comparator.comparing(MealPlanItem::getMealType))
              .collect(Collectors.toList());

      List<MealPlanItemView> viewItems =
          dayItems.stream()
              .map(
                  i -> {
                    Recipe r = recipes.get(i.getRecipeId());
                    MealPlanItemView v = new MealPlanItemView();
                    v.setMealType(i.getMealType());
                    v.setNotes(i.getNotes());
                    v.setRecipe(toRecipeView(r));
                    return v;
                  })
              .collect(Collectors.toList());

      MealPlanDayView dayView = new MealPlanDayView();
      dayView.setDayIndex(day);
      dayView.setItems(viewItems);
      days.add(dayView);
    }

    MealPlanResponse resp = new MealPlanResponse();
    resp.setPlanId(plan.getId());
    resp.setUserId(plan.getUserId());
    resp.setWeekStart(plan.getWeekStart());
    resp.setStrategy(plan.getStrategy());
    resp.setDays(days);
    return resp;
  }

  private RecipeView toRecipeView(Recipe r) {
    RecipeView v = new RecipeView();
    v.setId(r.getId());
    v.setName(r.getName());
    v.setSuggestedMealType(r.getSuggestedMealType());
    v.setPrepMinutes(r.getPrepMinutes());
    v.setTags(CsvUtil.split(r.getTagsCsv()));
    v.setCalories(r.getCalories());
    v.setProteinG(r.getProteinG());
    v.setCarbsG(r.getCarbsG());
    v.setFatG(r.getFatG());
    return v;
  }
}

