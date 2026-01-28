package com.example.imo.repo;

import com.example.imo.domain.MealPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MealPlanItemRepository extends JpaRepository<MealPlanItem, UUID> {
  List<MealPlanItem> findByMealPlanIdOrderByDayIndexAsc(UUID mealPlanId);
}

