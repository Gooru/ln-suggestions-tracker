package org.gooru.suggestions.processor.listsuggestions;

import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.constants.HttpConstants.HttpStatus;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.processor.data.SuggestionArea;
import org.gooru.suggestions.processor.data.SuggestionOrigin;
import org.gooru.suggestions.processor.utilities.ConverterUtils;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish
 */
class ListUserSuggestionsInClassCommand {

  private UUID userId;
  private UUID classId;
  private String scope;
  private PaginationInfo paginationInfo;
  private String suggestionOrigin;

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

  public String getSuggestionOrigin() {
    return suggestionOrigin;
  }

  private static ListUserSuggestionsInClassCommand buildFromJsonObject(JsonObject input) {
    ListUserSuggestionsInClassCommand command = new ListUserSuggestionsInClassCommand();
    try {
      command.userId = ConverterUtils.convertToUuid(input, ListUserSuggestionsInClassCommand.CommandAttributes.USER_ID);
      command.classId = ConverterUtils.convertToUuid(input, ListUserSuggestionsInClassCommand.CommandAttributes.CLASS_ID);
      command.scope = input.getString(CommandAttributes.SCOPE);
      command.suggestionOrigin = input.getString(CommandAttributes.SUGGESTION_ORIGIN);
      command.paginationInfo = PaginationInfo.buildFromRequest(input);
    } catch (IllegalArgumentException e) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, e.getMessage());
    }

    return command;
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
    bean.setSuggestionOrigin(suggestionOrigin);
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
    if (suggestionOrigin != null && !suggestionOrigin.isEmpty()) {
      try {
        SuggestionOrigin suggestionOrigin = SuggestionOrigin.builder(this.suggestionOrigin);
      } catch (IllegalArgumentException e) {
        throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid suggestionOrigin");
      }
    }
  }

  static class CommandAttributes {

    public static final String SCOPE = "scope";
    private static final String USER_ID = "userId";
    private static final String CLASS_ID = "classId";
    private static final String SUGGESTION_ORIGIN = "suggestionOrigin";
  }

  public static final class UserSuggestionsInClassBean {

    private UUID userId;
    private UUID classId;
    private String scope;
    private String suggestionOrigin;

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

    public String getSuggestionOrigin() {
      return suggestionOrigin;
    }

    public void setSuggestionOrigin(String suggestionOrigin) {
      this.suggestionOrigin = suggestionOrigin;
    }
  }
}
