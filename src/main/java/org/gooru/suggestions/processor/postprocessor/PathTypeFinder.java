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
      return PathType.Ca_Teacher.getName();
    } else if (suggestionOrigin.equalsIgnoreCase(SYSTEM)) {
      return PathType.Ca_System.getName();
    }
    return null;
  }
  
  static String fetchPathTypeForProficiency(String suggestionOrigin) {
    if (suggestionOrigin.equalsIgnoreCase(TEACHER)) {
      return PathType.Proficiency_Teacher.getName();
    } else if (suggestionOrigin.equalsIgnoreCase(SYSTEM)) {
      return PathType.Proficiency_System.getName();
    }
    return null;
  }
  
  static String fetchPathTypeForCoursemap(String suggestionOrigin) {
    if (suggestionOrigin.equalsIgnoreCase(TEACHER)) {
      return PathType.Teacher.getName();
    } else if (suggestionOrigin.equalsIgnoreCase(SYSTEM)) {
      return PathType.System.getName();
    }
    return null;
  }

}
