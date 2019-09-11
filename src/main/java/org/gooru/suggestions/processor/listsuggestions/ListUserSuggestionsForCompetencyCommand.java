package org.gooru.suggestions.processor.listsuggestions;

import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.constants.HttpConstants.HttpStatus;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import io.vertx.core.json.JsonObject;

/**
 * @author renuka
 */
class ListUserSuggestionsForCompetencyCommand {

  private UUID userId;
  private UUID classId;
  private String codeId;
  private PaginationInfo paginationInfo;

  public UUID getUserId() {
    return userId;
  }

  public String getCodeId() {
    return codeId;
  }

  public UUID getClassId() {
    return classId;
  }
  
  public PaginationInfo getPaginationInfo() {
    return paginationInfo;
  }

  private static ListUserSuggestionsForCompetencyCommand buildFromJsonObject(JsonObject input) {
    ListUserSuggestionsForCompetencyCommand command = new ListUserSuggestionsForCompetencyCommand();
    try {
      command.userId = toUuid(input, CommandAttributes.USER_ID);
      command.codeId = input.getString(CommandAttributes.CODE_ID);
      command.classId = toUuid(input, CommandAttributes.CLASS_ID);
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

  public static ListUserSuggestionsForCompetencyCommand builder(JsonObject input) {
    ListUserSuggestionsForCompetencyCommand result = ListUserSuggestionsForCompetencyCommand
        .buildFromJsonObject(input);
    result.validate();
    return result;
  }

  public UserSuggestionsForCompetencyBean asBean() {
    UserSuggestionsForCompetencyBean bean = new UserSuggestionsForCompetencyBean();
    bean.setCodeId(codeId);
    bean.setUserId(userId);
    bean.setClassId(classId);
    return bean;
  }

  private void validate() {
    if (codeId == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid codeId");
    } else if (userId == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid userId");
    }
  }

  static class CommandAttributes {
    private static final String USER_ID = "userId";
    private static final String CODE_ID = "codeId";
    private static final String CLASS_ID = "classId";
  }

  public static final class UserSuggestionsForCompetencyBean {

    private UUID userId;
    private String codeId;
    private UUID classId;

    public UUID getUserId() {
      return userId;
    }

    public void setUserId(UUID userId) {
      this.userId = userId;
    }

    public String getCodeId() {
      return codeId;
    }

    public void setCodeId(String codeId) {
      this.codeId = codeId;
    }

    public UUID getClassId() {
      return classId;
    }

    public void setClassId(UUID classId) {
      this.classId = classId;
    }

  }
}
