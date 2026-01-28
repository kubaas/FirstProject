package com.example.imo;

import com.example.imo.config.RecipeCatalogProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RecipeCatalogProperties.class)
public class ImoApplication {
  public static void main(String[] args) {
    SpringApplication.run(ImoApplication.class, args);
  }
}

