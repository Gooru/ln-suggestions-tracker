package org.gooru.suggestions.processor.data;

import java.util.HashMap;
import java.util.Map;

public enum SuggestionOrigin {
  System("system"),
  Teacher("teacher");

  private final String name;

  SuggestionOrigin(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  private static final Map<String, SuggestionOrigin> LOOKUP = new HashMap<>(values().length);

  static {
    for (SuggestionOrigin suggestionOrigin : values()) {
      LOOKUP.put(suggestionOrigin.name, suggestionOrigin);
    }
  }

  public static SuggestionOrigin builder(String type) {
    SuggestionOrigin result = LOOKUP.get(type);
    if (result == null) {
      throw new IllegalArgumentException("Invalid suggestion origin type");
    }
    return result;
  }
}
