package com.example.imo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class CsvUtil {
  private CsvUtil() {}

  public static String join(List<String> items) {
    if (items == null || items.isEmpty()) {
      return "";
    }
    return items.stream()
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.joining(","));
  }

  public static List<String> split(String csv) {
    if (csv == null || csv.trim().isEmpty()) {
      return Collections.emptyList();
    }
    List<String> out = new ArrayList<>();
    Arrays.stream(csv.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .forEach(out::add);
    return out;
  }
}

