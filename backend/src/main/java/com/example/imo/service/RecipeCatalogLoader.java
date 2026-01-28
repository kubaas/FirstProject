package com.example.imo.service;

import com.example.imo.config.RecipeCatalogProperties;
import com.example.imo.domain.MealType;
import com.example.imo.domain.Recipe;
import com.example.imo.repo.RecipeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RecipeCatalogLoader {

  private final RecipeRepository recipeRepository;
  private final RecipeCatalogProperties properties;
  private final ResourceLoader resourceLoader;
  private final ObjectMapper objectMapper;

  public RecipeCatalogLoader(
      RecipeRepository recipeRepository,
      RecipeCatalogProperties properties,
      ResourceLoader resourceLoader,
      ObjectMapper objectMapper) {
    this.recipeRepository = recipeRepository;
    this.properties = properties;
    this.resourceLoader = resourceLoader;
    this.objectMapper = objectMapper;
  }

  @PostConstruct
  public void loadIfEmpty() throws Exception {
    if (recipeRepository.count() > 0) {
      return;
    }

    Resource res = resourceLoader.getResource(properties.getCatalogResource());
    try (InputStream is = res.getInputStream()) {
      List<RecipeJson> items = objectMapper.readValue(is, new TypeReference<List<RecipeJson>>() {});
      List<Recipe> recipes =
          items.stream()
              .map(
                  r ->
                      new Recipe(
                          UUID.fromString(r.id),
                          r.name,
                          MealType.valueOf(r.suggestedMealType),
                          r.prepMinutes,
                          String.join(",", r.tags),
                          r.calories,
                          r.proteinG,
                          r.carbsG,
                          r.fatG))
              .collect(Collectors.toList());
      recipeRepository.saveAll(recipes);
    }
  }

  static class RecipeJson {
    public String id;
    public String name;
    public String suggestedMealType;
    public int prepMinutes;
    public List<String> tags;
    public int calories;
    public double proteinG;
    public double carbsG;
    public double fatG;
  }
}

