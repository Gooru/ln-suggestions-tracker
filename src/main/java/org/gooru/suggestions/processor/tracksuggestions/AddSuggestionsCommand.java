package org.gooru.suggestions.processor.tracksuggestions;

import io.vertx.core.json.JsonObject;
import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.processor.data.SuggestedContentType;
import org.gooru.suggestions.processor.data.SuggestedTo;
import org.gooru.suggestions.processor.data.SuggestionArea;
import org.gooru.suggestions.processor.data.SuggestionCriteria;
import org.gooru.suggestions.processor.data.SuggestionOrigin;
import org.gooru.suggestions.processor.data.TxCodeType;

/**
 * @author ashish
 */
class AddSuggestionsCommand {

  private UUID userId;
  private UUID courseId;
  private UUID unitId;
  private UUID lessonId;
  private UUID classId;
  private UUID collectionId;
  private Long caContentId;
  private UUID suggestedContentId;
  private SuggestionOrigin suggestionOrigin;
  private UUID suggestionOriginatorId;
  private SuggestionCriteria suggestionCriteria;
  private SuggestedContentType suggestedContentType;
  private SuggestedTo suggestedTo;
  private Boolean accepted;
  private SuggestionArea suggestionArea;
  private String txCode;
  private TxCodeType txCodeType;


  static AddSuggestionsCommand builder(JsonObject input) {
    AddSuggestionsCommand result = buildFromJsonObject(input);
    result.validate();
    return result;
  }

  private void validate() {
    if (userId == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid user id");
    }
    if (suggestedContentId == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid content id for suggestion");
    }
    if (suggestedContentType == null) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid suggested content type");
    }
    if (suggestionArea == SuggestionArea.CourseMap) {
      if (courseId == null || unitId == null || lessonId == null || collectionId == null) {
        throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
            "CULC should be present for Course Map suggestion");
      }
    }
    if (suggestionArea == SuggestionArea.Proficiency) {
      if (classId == null || ((txCode == null && txCodeType != null) || (txCodeType == null && txCode != null))) {
        throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
            "All class_id, tx_code and tx_code_type should be present for Proficiency Suggestion");
      }
    }
    if (suggestionArea == SuggestionArea.ClassActivity) {
      if (classId == null || caContentId == null || collectionId == null) {
        throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
            "ClassId, caContentId, collectionId should be present for Classactivity suggestion");
      }
    }
  }

  private static AddSuggestionsCommand buildFromJsonObject(JsonObject input) {
    AddSuggestionsCommand command = new AddSuggestionsCommand();

    try {
      command.userId = toUuid(input, CommandAttributes.USER_ID);
      command.courseId = toUuid(input, CommandAttributes.COURSE_ID);
      command.unitId = toUuid(input, CommandAttributes.UNIT_ID);
      command.lessonId = toUuid(input, CommandAttributes.LESSON_ID);
      command.classId = toUuid(input, CommandAttributes.CLASS_ID);
      command.collectionId = toUuid(input, CommandAttributes.COLLECTION_ID);
      command.caContentId = input.getLong(CommandAttributes.CA_CONTENT_ID);
      command.suggestedContentId = toUuid(input, CommandAttributes.SUGGESTED_CONTENT_ID);
      command.suggestionOrigin =
          SuggestionOrigin.builder(input.getString(CommandAttributes.SUGGESTION_ORIGIN));
      command.suggestionOriginatorId = toUuid(input, CommandAttributes.SUGGESTION_ORIGINATOR_ID);
      command.suggestionCriteria =
          SuggestionCriteria.builder(input.getString(CommandAttributes.SUGGESTION_CRITERIA));
      command.suggestedContentType =
          SuggestedContentType.builder(input.getString(CommandAttributes.SUGGESTED_CONTENT_TYPE));
      command.suggestedTo = SuggestedTo.builder(input.getString(CommandAttributes.SUGGESTED_TO));
      command.suggestionArea =
          SuggestionArea.builder(input.getString(CommandAttributes.SUGGESTION_AREA));
      command.txCode = input.getString(CommandAttributes.TX_CODE);
      command.txCodeType = toTxCodeType(input.getString(CommandAttributes.TX_CODE_TYPE));

    } catch (IllegalArgumentException e) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, e.getMessage());
    }

    return command;
  }

  private static TxCodeType toTxCodeType(String txCodeType) {
    if (txCodeType != null) {
      return TxCodeType.builder(txCodeType);
    }
    return null;
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

  private static Long toLong(JsonObject input, String key) {
    String value = input.getString(key);
    return convertStringToLong(value);
  }

  private static Long convertStringToLong(String value) {
    if (value == null || value.isEmpty()) {
      return null;
    }
    return Long.valueOf(value);
  }

  public UUID getUserId() {
    return userId;
  }

  public UUID getCourseId() {
    return courseId;
  }

  public UUID getUnitId() {
    return unitId;
  }

  public UUID getLessonId() {
    return lessonId;
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

  public UUID getSuggestedContentId() {
    return suggestedContentId;
  }

  public SuggestionOrigin getSuggestionOrigin() {
    return suggestionOrigin;
  }

  public UUID getSuggestionOriginatorId() {
    return suggestionOriginatorId;
  }

  public SuggestionCriteria getSuggestionCriteria() {
    return suggestionCriteria;
  }

  public SuggestedContentType getSuggestedContentType() {
    return suggestedContentType;
  }

  public SuggestedTo getSuggestedTo() {
    return suggestedTo;
  }

  public Boolean getAccepted() {
    return accepted;
  }

  public SuggestionArea getSuggestionArea() {
    return suggestionArea;
  }

  public String getTxCode() {
    return txCode;
  }

  public TxCodeType getTxCodeType() {
    return txCodeType;
  }

  private static final class CommandAttributes {

    private static final String USER_ID = "user_id";
    private static final String COURSE_ID = "course_id";
    private static final String UNIT_ID = "unit_id";
    private static final String LESSON_ID = "lesson_id";
    private static final String COLLECTION_ID = "collection_id";
    private static final String CA_CONTENT_ID = "ca_content_id";
    private static final String CLASS_ID = "class_id";
    private static final String SUGGESTED_CONTENT_ID = "suggested_content_id";
    private static final String SUGGESTED_CONTENT_TYPE = "suggested_content_type";
    private static final String SUGGESTION_ORIGIN = "suggestion_origin";
    private static final String SUGGESTION_ORIGINATOR_ID = "suggestion_originator_id";
    private static final String SUGGESTION_CRITERIA = "suggestion_criteria";
    private static final String SUGGESTED_TO = "suggested_to";
    private static final String SUGGESTION_AREA = "suggestion_area";
    private static final String TX_CODE = "tx_code";
    private static final String TX_CODE_TYPE = "tx_code_type";

    private CommandAttributes() {
      throw new AssertionError();
    }
  }

  AddSuggestionBean getBean() {
    AddSuggestionBean result = new AddSuggestionBean();

    result.setUserId(userId);
    result.setCourseId(courseId);
    result.setUnitId(unitId);
    result.setLessonId(lessonId);
    result.setCollectionId(collectionId);
    result.setCaContentId(caContentId);
    result.setClassId(classId);
    result.setSuggestedContentId(suggestedContentId);
    result.setSuggestionOrigin(suggestionOrigin != null ? suggestionOrigin.getName() : null);
    result.setSuggestionOriginatorId(suggestionOriginatorId);
    result.setSuggestionCriteria(suggestionCriteria != null ? suggestionCriteria.getName() : null);
    result.setSuggestedContentType(suggestedContentType.getName());
    result.setSuggestedTo(suggestedTo != null ? suggestedTo.getName() : null);
    result.setSuggestionArea(suggestionArea != null ? suggestionArea.getName() : null);
    result.setTxCode(txCode);
    result.setTxCodeType(txCodeType != null ? txCodeType.getName() : null);

    return result;
  }
}
