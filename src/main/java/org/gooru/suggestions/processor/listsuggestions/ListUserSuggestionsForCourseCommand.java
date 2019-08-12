package org.gooru.suggestions.processor.listsuggestions;

import io.vertx.core.json.JsonObject;
import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.constants.HttpConstants.HttpStatus;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.processor.data.SuggestionArea;

/**
 * @author ashish
 */
class ListUserSuggestionsForCourseCommand {

  private UUID userId;
  private UUID courseId;
  private String scope;
  private PaginationInfo paginationInfo;

  public UUID getUserId() {
    return userId;
  }

  public UUID getCourseId() {
    return courseId;
  }

  public String getScope() {
    return scope;
  }

  public PaginationInfo getPaginationInfo() {
    return paginationInfo;
  }

  private static ListUserSuggestionsForCourseCommand buildFromJsonObject(JsonObject input) {
    ListUserSuggestionsForCourseCommand command = new ListUserSuggestionsForCourseCommand();
    try {
      command.userId = toUuid(input, CommandAttributes.USER_ID);
      command.courseId = toUuid(input, CommandAttributes.COURSE_ID);
      command.scope = input.getString(CommandAttributes.SCOPE);
      command.paginationInfo = PaginationInfo.buildFromRequest(input);
    } catch (IllegalArgumentException e) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, e.getMessage());
    }
    return command;
  }

  private static UUID toUuid(JsonObject input, String key) {
    String value = input.getString(key);
    if (value == null || value.isEmpty()) {
      return null;
    }
    return UUID.fromString(value);
  }

  public static ListUserSuggestionsForCourseCommand builder(JsonObject input) {
    ListUserSuggestionsForCourseCommand result = ListUserSuggestionsForCourseCommand
        .buildFromJsonObject(input);
    result.validate();
    return result;
  }

  public UserSuggestionsForCourseBean asBean() {
    UserSuggestionsForCourseBean bean = new UserSuggestionsForCourseBean();
    bean.setCourseId(courseId);
    bean.setUserId(userId);
    bean.setScope(scope);
    return bean;
  }

  private void validate() {
    if (courseId == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid courseId");
    } else if (userId == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid userId");
    }
    if (scope != null && !scope.isEmpty()) {
      try {
        SuggestionArea scopedArea = SuggestionArea.builder(scope);
      } catch (IllegalArgumentException e) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid scope");
      }
    }
  }

  static class CommandAttributes {

    public static final String SCOPE = "scope";
    private static final String USER_ID = "userId";
    private static final String COURSE_ID = "courseId";
  }

  public static final class UserSuggestionsForCourseBean {

    private UUID userId;
    private UUID courseId;
    private String scope;

    public UUID getUserId() {
      return userId;
    }

    public void setUserId(UUID userId) {
      this.userId = userId;
    }

    public UUID getCourseId() {
      return courseId;
    }

    public void setCourseId(UUID courseId) {
      this.courseId = courseId;
    }

    public String getScope() {
      return scope;
    }

    public void setScope(String scope) {
      this.scope = scope;
    }
  }
}
