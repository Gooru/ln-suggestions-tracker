package org.gooru.suggestions.processor.usersuggestions;

import io.vertx.core.json.JsonObject;
import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;

/**
 * @author ashish on 24/11/17.
 */
class UserSuggestionsForCourseCommand {

  private UUID userId;
  private UUID courseId;

  public UUID getUserId() {
    return userId;
  }

  public UUID getCourseId() {
    return courseId;
  }

  private static UserSuggestionsForCourseCommand buildFromJsonObject(JsonObject input) {
    UserSuggestionsForCourseCommand command = new UserSuggestionsForCourseCommand();
    try {
      command.userId = toUuid(input, UserSuggestionsForCourseCommand.CommandAttributes.USER_ID);
      command.courseId = toUuid(input, UserSuggestionsForCourseCommand.CommandAttributes.COURSE_ID);
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

  public static UserSuggestionsForCourseCommand builder(JsonObject input) {
    UserSuggestionsForCourseCommand result = UserSuggestionsForCourseCommand
        .buildFromJsonObject(input);
    result.validate();
    return result;
  }

  public UserSuggestionsForCourseBean asBean() {
    UserSuggestionsForCourseBean bean = new UserSuggestionsForCourseBean();
    bean.setCourseId(courseId);
    bean.setUserId(userId);
    return bean;
  }

  private void validate() {
    if (courseId == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid courseId");
    } else if (userId == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid userId");
    }
  }

  static class CommandAttributes {

    private static final String USER_ID = "userId";
    private static final String COURSE_ID = "courseId";
  }

  public static final class UserSuggestionsForCourseBean {

    private UUID userId;
    private UUID courseId;

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
  }
}
