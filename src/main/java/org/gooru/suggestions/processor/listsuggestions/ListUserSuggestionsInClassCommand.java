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
class ListUserSuggestionsInClassCommand {

  private UUID userId;
  private UUID classId;
  private String scope;
  private PaginationInfo paginationInfo;

  public UUID getUserId() {
    return userId;
  }

  public UUID getClassId() {
    return classId;
  }

  public String getScope() {
    return scope;
  }

  public PaginationInfo getPaginationInfo() {
    return paginationInfo;
  }

  private static ListUserSuggestionsInClassCommand buildFromJsonObject(JsonObject input) {
    ListUserSuggestionsInClassCommand command = new ListUserSuggestionsInClassCommand();
    try {
      command.userId = toUuid(input, ListUserSuggestionsInClassCommand.CommandAttributes.USER_ID);
      command.classId = toUuid(input, ListUserSuggestionsInClassCommand.CommandAttributes.CLASS_ID);
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

  public static ListUserSuggestionsInClassCommand builder(JsonObject input) {
    ListUserSuggestionsInClassCommand result = ListUserSuggestionsInClassCommand.buildFromJsonObject(input);
    result.validate();
    return result;
  }

  public UserSuggestionsInClassBean asBean() {
    UserSuggestionsInClassBean bean = new UserSuggestionsInClassBean();
    bean.setClassId(classId);
    bean.setUserId(userId);
    bean.setScope(scope);
    return bean;
  }

  private void validate() {
    if (classId == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid classId");
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
    private static final String CLASS_ID = "classId";
  }

  public static final class UserSuggestionsInClassBean {

    private UUID userId;
    private UUID classId;
    private String scope;

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

    public String getScope() {
      return scope;
    }

    public void setScope(String scope) {
      this.scope = scope;
    }
  }
}
