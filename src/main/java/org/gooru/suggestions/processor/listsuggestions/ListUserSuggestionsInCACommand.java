package org.gooru.suggestions.processor.listsuggestions;

import java.util.List;
import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.constants.HttpConstants.HttpStatus;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.processor.utilities.ConverterUtils;
import io.vertx.core.json.JsonObject;

/**
 * @author renuka
 */
class ListUserSuggestionsInCACommand {

  private UUID userId;
  private UUID classId;
  private List<Long> caIds;
  private Boolean detail;
  private PaginationInfo paginationInfo;

  public UUID getUserId() {
    return userId;
  }

  public UUID getClassId() {
    return classId;
  }

  public List<Long> getCaIds() {
    return caIds;
  }

  public Boolean getDetail() {
    return detail;
  }
  
  public PaginationInfo getPaginationInfo() {
    return paginationInfo;
  }

  private static ListUserSuggestionsInCACommand buildFromJsonObject(JsonObject input) {
    ListUserSuggestionsInCACommand command = new ListUserSuggestionsInCACommand();
    try {
      command.userId = ConverterUtils.convertToUuid(input, CommandAttributes.USER_ID);
      command.classId = ConverterUtils.convertToUuid(input, CommandAttributes.CLASS_ID);
      command.caIds = ConverterUtils.convertFromJsonArrayToListOfLong(input, CommandAttributes.CA_IDS);
      command.paginationInfo = PaginationInfo.buildFromRequest(input);
      command.detail = ConverterUtils.convertToBoolean(input, CommandAttributes.DETAIL);
    } catch (IllegalArgumentException e) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, e.getMessage());
    }
    return command;
  }

  public static ListUserSuggestionsInCACommand builder(JsonObject input) {
    ListUserSuggestionsInCACommand result = ListUserSuggestionsInCACommand
        .buildFromJsonObject(input);
    result.validate();
    return result;
  }

  public UserSuggestionsForCompetencyBean asBean() {
    UserSuggestionsForCompetencyBean bean = new UserSuggestionsForCompetencyBean();
    bean.setCaIds(caIds);
    bean.setClassId(classId);
    bean.setUserId(userId);
    bean.setDetail(detail);
    return bean;
  }

  private void validate() {
    if (classId == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid classId");
    } else if (caIds == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid caId");
    }
  }

  static class CommandAttributes {
    private static final String USER_ID = "userId";
    private static final String CLASS_ID = "classId";
    private static final String CA_IDS = "caIds";
    private static final String DETAIL = "detail";
  }

  public static final class UserSuggestionsForCompetencyBean {

    private UUID userId;
    private UUID classId;
    private List<Long> caIds;
    private Boolean detail;

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

    public List<Long> getCaIds() {
      return caIds;
    }

    public void setCaIds(List<Long> caIds) {
      this.caIds = caIds;
    }

    public Boolean getDetail() {
      return detail;
    }

    public void setDetail(Boolean detail) {
      this.detail = detail;
    }
  }
}
