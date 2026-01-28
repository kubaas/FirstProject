package com.example.imo.repo;

import com.example.imo.domain.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MealPlanRepository extends JpaRepository<MealPlan, UUID> {
  Optional<MealPlan> findTopByUserIdOrderByCreatedAtDesc(UUID userId);
}

