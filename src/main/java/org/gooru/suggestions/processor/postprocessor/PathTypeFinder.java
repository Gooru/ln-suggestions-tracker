package org.gooru.suggestions.processor.postprocessor;

/**
 * @author renuka
 */

final class PathTypeFinder {
  
  public static final String CLASS_ACTIVITY = "class-activity";
  public static final String PROFICIENCY = "proficiency";
  public static final String COURSEMAP = "coursemap";

  public static final String TEACHER = "teacher";
  public static final String SYSTEM = "system";
  
  private PathTypeFinder() {
    throw new AssertionError();
  }  
  
  static String findPathTypeForSource(String source, String suggestionOrigin) {
    String pathType = null;
    switch (source) {
      case CLASS_ACTIVITY:
        pathType = fetchPathTypeForClassActivity(suggestionOrigin);
        break;
      case PROFICIENCY:
        pathType = fetchPathTypeForProficiency(suggestionOrigin);
        break;
      case COURSEMAP:
        pathType = fetchPathTypeForCoursemap(suggestionOrigin);
        break;
    }
    return pathType;
  }
  
  static String fetchPathTypeForClassActivity(String suggestionOrigin) {
    if (suggestionOrigin.equalsIgnoreCase(TEACHER)) {
      return PathType.CA_TEACHER.getName();
    } else if (suggestionOrigin.equalsIgnoreCase(SYSTEM)) {
      return PathType.CA_SYSTEM.getName();
    }
    return null;
  }
  
  static String fetchPathTypeForProficiency(String suggestionOrigin) {
    if (suggestionOrigin.equalsIgnoreCase(TEACHER)) {
      return PathType.PROFICIENCY_TEACHER.getName();
    } else if (suggestionOrigin.equalsIgnoreCase(SYSTEM)) {
      return PathType.PROFICIENCY_SYSTEM.getName();
    }
    return null;
  }
  
  static String fetchPathTypeForCoursemap(String suggestionOrigin) {
    if (suggestionOrigin.equalsIgnoreCase(TEACHER)) {
      return PathType.TEACHER.getName();
    } else if (suggestionOrigin.equalsIgnoreCase(SYSTEM)) {
      return PathType.SYSTEM.getName();
    }
    return null;
  }

}
