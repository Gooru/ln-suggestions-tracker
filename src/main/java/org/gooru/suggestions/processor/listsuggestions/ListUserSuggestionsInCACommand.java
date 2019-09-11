package org.gooru.suggestions.processor.listsuggestions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.constants.HttpConstants.HttpStatus;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author renuka
 */
class ListUserSuggestionsInCACommand {

  private UUID userId;
  private UUID classId;
  private UUID collectionId;
  private List<Long> caContentIds;
  private Long caContentId;
  private PaginationInfo paginationInfo;

  public UUID getUserId() {
    return userId;
  }

  public UUID getClassId() {
    return classId;
  }

  public UUID getCollectionId() {
    return collectionId;
  }
  
  public Long getCaContentId() {
    return caContentId;
  }

  public List<Long> getCaContentIds() {
    return caContentIds;
  }

  public PaginationInfo getPaginationInfo() {
    return paginationInfo;
  }

  private static ListUserSuggestionsInCACommand buildFromJsonObject(JsonObject input) {
    ListUserSuggestionsInCACommand command = new ListUserSuggestionsInCACommand();
    try {
      command.userId = toUuid(input, CommandAttributes.USER_ID);
      command.classId = toUuid(input, CommandAttributes.CLASS_ID);
      command.caContentIds = toListOfLong(input, CommandAttributes.CA_CONTENT_IDS);
      command.collectionId = toUuid(input, CommandAttributes.COLLECTION_ID);
      command.caContentId = input.getLong(CommandAttributes.CA_CONTENT_ID);
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

  private static List<Long> toListOfLong(JsonObject input, String key) {
    JsonArray value = input.getJsonArray(key);
    if (value == null) {
      return null;
    }
    List<Long> listOfLong = new ArrayList<>(value.size());
    value.forEach(v -> {
      listOfLong.add(Long.valueOf(v.toString()));
    });
    return listOfLong;
  }

  public static ListUserSuggestionsInCACommand builder(JsonObject input) {
    ListUserSuggestionsInCACommand result = ListUserSuggestionsInCACommand
        .buildFromJsonObject(input);
    result.validate();
    return result;
  }

  public UserSuggestionsForCompetencyBean asBean() {
    UserSuggestionsForCompetencyBean bean = new UserSuggestionsForCompetencyBean();
    bean.setCaContentIds(caContentIds);
    bean.setCaContentId(caContentId);
    bean.setCollectionId(collectionId);
    bean.setClassId(classId);
    bean.setUserId(userId);
    return bean;
  }

  private void validate() {
    if (userId == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid userId");
    } else if (classId == null) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid classId");
    } else if ((caContentIds == null) && (caContentId == null || collectionId == null)) {
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Invalid caContentId");
    }
  }

  static class CommandAttributes {
    private static final String USER_ID = "userId";
    private static final String CLASS_ID = "classId";
    private static final String COLLECTION_ID = "collectionId";
    private static final String CA_CONTENT_ID = "caContentId";
    private static final String CA_CONTENT_IDS = "caContentIds";
  }

  public static final class UserSuggestionsForCompetencyBean {

    private UUID userId;
    private UUID classId;
    private UUID collectionId;
    private List<Long> caContentIds;
    private Long caContentId;

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

    public UUID getCollectionId() {
      return collectionId;
    }

    public void setCollectionId(UUID collectionId) {
      this.collectionId = collectionId;
    }

    public Long getCaContentId() {
      return caContentId;
    }

    public void setCaContentId(Long caContentId) {
      this.caContentId = caContentId;
    }

    public List<Long> getCaContentIds() {
      return caContentIds;
    }

    public void setCaContentIds(List<Long> caContentIds) {
      this.caContentIds = caContentIds;
    }

  }
}
