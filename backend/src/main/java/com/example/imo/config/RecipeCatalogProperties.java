package com.example.imo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "imo.recipes")
public class RecipeCatalogProperties {
  private String catalogResource;

  public String getCatalogResource() {
    return catalogResource;
  }

  public void setCatalogResource(String catalogResource) {
    this.catalogResource = catalogResource;
  }
}

