package com.example.imo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.imo.service.RecipeCatalogLoader;

@Component
public class DataInitializer implements CommandLineRunner {

  private final RecipeCatalogLoader recipeCatalogLoader;

  public DataInitializer(RecipeCatalogLoader recipeCatalogLoader) {
    this.recipeCatalogLoader = recipeCatalogLoader;
  }

  @Override
  public void run(String... args) throws Exception {
    // Load recipes from catalog.json on startup
    try {
      recipeCatalogLoader.loadRecipes();
    } catch (Exception e) {
      // Recipes might already be loaded, ignore
    }
  }
}
