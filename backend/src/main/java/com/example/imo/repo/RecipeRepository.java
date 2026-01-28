package com.example.imo.repo;

import com.example.imo.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
  List<Recipe> findBySuggestedMealType(com.example.imo.domain.MealType mealType);
}

