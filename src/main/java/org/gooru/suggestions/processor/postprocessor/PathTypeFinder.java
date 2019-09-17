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
  
  static String findPathTypeForSource(String source, String suggestionType) {
    String pathType = null;
    switch (source) {
      case CLASS_ACTIVITY:
        pathType = fetchPathTypeForClassActivity(suggestionType);
        break;
      case PROFICIENCY:
        pathType = fetchPathTypeForProficiency(suggestionType);
        break;
      case COURSEMAP:
        pathType = fetchPathTypeForCoursemap(suggestionType);
        break;
    }
    return pathType;
  }
  
  static String fetchPathTypeForClassActivity(String suggestionType) {
    String pathType = PathType.Ca_Teacher.getName();
    if (suggestionType.equalsIgnoreCase(SYSTEM)) {
      pathType = PathType.Ca_System.getName();
    }
    return pathType;
  }
  
  static String fetchPathTypeForProficiency(String suggestionType) {
    String pathType = PathType.Proficiency_Teacher.getName();
    if (suggestionType.equalsIgnoreCase(SYSTEM)) {
      pathType = PathType.Proficiency_System.getName();
    }
    return pathType;
  }
  
  static String fetchPathTypeForCoursemap(String suggestionType) {
    String pathType = PathType.Teacher.getName();
    if (suggestionType.equalsIgnoreCase(SYSTEM)) {
      pathType = PathType.System.getName();
    }
    return pathType;
  }

}
