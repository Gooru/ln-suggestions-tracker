package org.gooru.suggestions.processor.usersuggestions;

import io.vertx.core.json.JsonObject;
import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;

/**
 * @author ashish on 24/11/17.
 */
class UserSuggestionsInClassCommand {

  private UUID userId;
  private UUID classId;

  public UUID getUserId() {
    return userId;
  }

  public UUID getClassId() {
    return classId;
  }

  private static UserSuggestionsInClassCommand buildFromJsonObject(JsonObject input) {
    UserSuggestionsInClassCommand command = new UserSuggestionsInClassCommand();
    try {
      command.userId = toUuid(input, UserSuggestionsInClassCommand.CommandAttributes.USER_ID);
      command.classId = toUuid(input, UserSuggestionsInClassCommand.CommandAttributes.CLASS_ID);
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

  public static UserSuggestionsInClassCommand builder(JsonObject input) {
    UserSuggestionsInClassCommand result = UserSuggestionsInClassCommand.buildFromJsonObject(input);
    result.validate();
    return result;
  }

  public UserSuggestionsInClassBean asBean() {
    UserSuggestionsInClassBean bean = new UserSuggestionsInClassBean();
    bean.setClassId(classId);
    bean.setUserId(userId);
    return bean;
  }

  private void validate() {
    if (classId == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid classId");
    } else if (userId == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid userId");
    }
  }

  static class CommandAttributes {

    private static final String USER_ID = "userId";
    private static final String CLASS_ID = "classId";
  }

  public static final class UserSuggestionsInClassBean {

    private UUID userId;
    private UUID classId;

    public UUID getUserId() {
      return userId;
    }

    public void setUserId(UUID userId) {
      this.userId = userId;
    }

    public UUID getClassId() {
      return classId;
    }

    public void setClassId(UUID classId) {
      this.classId = classId;
    }
  }
}
