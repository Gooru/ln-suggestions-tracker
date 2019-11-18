package org.gooru.suggestions.processor.data;

import java.util.HashMap;
import java.util.Map;

public enum SuggestionCriteria {
  Performance("performance"),
  Location("location");

  private final String name;

  SuggestionCriteria(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  private static final Map<String, SuggestionCriteria> LOOKUP = new HashMap<>(values().length);

  static {
    for (SuggestionCriteria suggestionCriteria : values()) {
      LOOKUP.put(suggestionCriteria.name, suggestionCriteria);
    }
  }

  public static SuggestionCriteria builder(String type) {
    SuggestionCriteria result = LOOKUP.get(type);
    if (result == null) {
      throw new IllegalArgumentException("Invalid suggestion criteria type");
    }
    return result;
  }
}
