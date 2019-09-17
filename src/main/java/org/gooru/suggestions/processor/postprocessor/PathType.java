package org.gooru.suggestions.processor.postprocessor;

import java.util.HashMap;
import java.util.Map;

public enum PathType {

  System("system"),
  Teacher("teacher"),
  Ca_System("ca.system"),
  Ca_Teacher("ca.teacher"),
  Proficiency_System("proficiency.system"),
  Proficiency_Teacher("proficiency.teacher");

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
