package org.gooru.suggestions.processor.postprocessor;

import java.util.HashMap;
import java.util.Map;

public enum PathType {

  SYSTEM("system"),
  TEACHER("teacher"),
  CA_SYSTEM("ca.system"),
  CA_TEACHER("ca.teacher"),
  PROFICIENCY_SYSTEM("proficiency.system"),
  PROFICIENCY_TEACHER("proficiency.teacher");

  private final String name;

  PathType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  private static final Map<String, PathType> LOOKUP = new HashMap<>(values().length);

  static {
    for (PathType suggestionType : values()) {
      LOOKUP.put(suggestionType.name, suggestionType);
    }
  }

  public static PathType builder(String type) {
    PathType result = LOOKUP.get(type);
    if (result == null) {
      throw new IllegalArgumentException("Invalid path type: " + type);
    }
    return result;
  }

}
