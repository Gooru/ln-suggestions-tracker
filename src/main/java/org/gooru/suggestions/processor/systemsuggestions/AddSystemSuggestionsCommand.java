package org.gooru.suggestions.processor.systemsuggestions;

import io.vertx.core.json.JsonObject;
import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.processor.data.SuggestedContentSubType;
import org.gooru.suggestions.processor.data.SuggestedContentType;

/**
 * @author ashish on 29/11/17.
 */
class AddSystemSuggestionsCommand {

  private UUID ctxUserId;
  private UUID ctxClassId;
  private UUID ctxCourseId;
  private UUID ctxUnitId;
  private UUID ctxLessonId;
  private UUID ctxCollectionId;
  private UUID suggestedContentId;
  private SuggestedContentType suggestedContentType;
  private SuggestedContentSubType suggestedContentSubType;

  static AddSystemSuggestionsCommand builder(JsonObject input) {
    AddSystemSuggestionsCommand result = buildFromJsonObject(input);
    result.validate();
    return result;
  }

  AddSystemSuggestionsBean getBean() {
    AddSystemSuggestionsBean result = new AddSystemSuggestionsBean();
    result.ctxUserId = ctxUserId;
    result.ctxClassId = ctxClassId;
    result.ctxCourseId = ctxCourseId;
    result.ctxUnitId = ctxUnitId;
    result.ctxLessonId = ctxLessonId;
    result.ctxCollectionId = ctxCollectionId;
    result.suggestedContentId = suggestedContentId;
    result.suggestedContentType =
        suggestedContentType != null ? suggestedContentType.getName() : null;
    result.suggestedContentSubType =
        suggestedContentSubType != null ? suggestedContentSubType.getName() : null;

    return result;
  }

  public UUID getCtxUserId() {
    return ctxUserId;
  }

  public UUID getCtxClassId() {
    return ctxClassId;
  }

  public UUID getCtxCourseId() {
    return ctxCourseId;
  }

  public UUID getCtxUnitId() {
    return ctxUnitId;
  }

  public UUID getCtxLessonId() {
    return ctxLessonId;
  }

  public UUID getCtxCollectionId() {
    return ctxCollectionId;
  }

  public UUID getSuggestedContentId() {
    return suggestedContentId;
  }

  public SuggestedContentType getSuggestedContentType() {
    return suggestedContentType;
  }

  public SuggestedContentSubType getSuggestedContentSubType() {
    return suggestedContentSubType;
  }

  private void validate() {
    if (ctxUserId == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid user id");
    } else if (ctxCourseId == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Course should be provided");
    } else if ((ctxUnitId == null || ctxLessonId == null)) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid unit or lesson for suggestion");
    } else if (suggestedContentId == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid content id for suggestion");
    } else if (suggestedContentType == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid suggested content type");
    }
  }

  private static AddSystemSuggestionsCommand buildFromJsonObject(JsonObject input) {
    AddSystemSuggestionsCommand command = new AddSystemSuggestionsCommand();

    try {
      command.ctxUserId = toUuid(input, CommandAttributes.USER_ID);
      command.ctxClassId = toUuid(input, CommandAttributes.CLASS_ID);
      command.ctxCourseId = toUuid(input, CommandAttributes.COURSE_ID);
      command.ctxUnitId = toUuid(input, CommandAttributes.UNIT_ID);
      command.ctxLessonId = toUuid(input, CommandAttributes.LESSON_ID);
      command.ctxCollectionId = toUuid(input, CommandAttributes.COLLECTION_ID);
      command.suggestedContentId = toUuid(input, CommandAttributes.SUGGESTED_CONTENT_ID);
      String value = input.getString(CommandAttributes.SUGGESTED_CONTENT_TYPE);
      command.suggestedContentType =
          (value != null && !value.isEmpty()) ? SuggestedContentType.builder(value) : null;
      value = input.getString(CommandAttributes.SUGGESTED_CONTENT_SUBTYPE);
      command.suggestedContentSubType =
          (value != null && !value.isEmpty()) ? SuggestedContentSubType.builder(value) : null;
    } catch (IllegalArgumentException e) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, e.getMessage());
    }

    return command;
  }

  private static UUID toUuid(JsonObject input, String key) {
    String value = input.getString(key);
    return convertStringToUuid(value);
  }

  private static UUID convertStringToUuid(String value) {
    if (value == null || value.isEmpty()) {
      return null;
    }
    return UUID.fromString(value);
  }

  static final class CommandAttributes {

    static final String USER_ID = "ctx_user_id";
    static final String CLASS_ID = "ctx_class_id";
    static final String COURSE_ID = "ctx_course_id";
    static final String LESSON_ID = "ctx_lesson_id";
    static final String UNIT_ID = "ctx_unit_id";
    static final String COLLECTION_ID = "ctx_collection_id";
    static final String SUGGESTED_CONTENT_ID = "suggested_content_id";
    static final String SUGGESTED_CONTENT_TYPE = "suggested_content_type";
    static final String SUGGESTED_CONTENT_SUBTYPE = "suggested_content_subtype";

    private CommandAttributes() {
      throw new AssertionError();
    }
  }

  public static final class AddSystemSuggestionsBean {

    private UUID ctxUserId;
    private UUID ctxClassId;
    private UUID ctxCourseId;
    private UUID ctxUnitId;
    private UUID ctxLessonId;
    private UUID ctxCollectionId;
    private UUID suggestedContentId;
    private String suggestedContentType;
    private String suggestedContentSubType;

    public UUID getCtxUserId() {
      return ctxUserId;
    }

    public void setCtxUserId(UUID ctxUserId) {
      this.ctxUserId = ctxUserId;
    }

    public UUID getCtxClassId() {
      return ctxClassId;
    }

    public void setCtxClassId(UUID ctxClassId) {
      this.ctxClassId = ctxClassId;
    }

    public UUID getCtxCourseId() {
      return ctxCourseId;
    }

    public void setCtxCourseId(UUID ctxCourseId) {
      this.ctxCourseId = ctxCourseId;
    }

    public UUID getCtxUnitId() {
      return ctxUnitId;
    }

    public void setCtxUnitId(UUID ctxUnitId) {
      this.ctxUnitId = ctxUnitId;
    }

    public UUID getCtxLessonId() {
      return ctxLessonId;
    }

    public void setCtxLessonId(UUID ctxLessonId) {
      this.ctxLessonId = ctxLessonId;
    }

    public UUID getCtxCollectionId() {
      return ctxCollectionId;
    }

    public void setCtxCollectionId(UUID ctxCollectionId) {
      this.ctxCollectionId = ctxCollectionId;
    }

    public UUID getSuggestedContentId() {
      return suggestedContentId;
    }

    public void setSuggestedContentId(UUID suggestedContentId) {
      this.suggestedContentId = suggestedContentId;
    }

    public String getSuggestedContentType() {
      return suggestedContentType;
    }

    public void setSuggestedContentType(String suggestedContentType) {
      this.suggestedContentType = suggestedContentType;
    }

    public String getSuggestedContentSubType() {
      return suggestedContentSubType;
    }

    public void setSuggestedContentSubType(String suggestedContentSubType) {
      this.suggestedContentSubType = suggestedContentSubType;
    }
  }
}
