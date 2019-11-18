package org.gooru.suggestions.processor.tracksuggestions;

import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.processor.data.SuggestedContentType;
import org.gooru.suggestions.processor.data.SuggestedTo;
import org.gooru.suggestions.processor.data.SuggestionArea;
import org.gooru.suggestions.processor.data.SuggestionCriteria;
import org.gooru.suggestions.processor.data.SuggestionOrigin;
import org.gooru.suggestions.processor.data.TxCodeType;
import org.gooru.suggestions.processor.utilities.ConverterUtils;
import io.vertx.core.json.JsonObject;

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
  private Long caId;
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
    if ((txCode == null && txCodeType != null) || (txCodeType == null && txCode != null)) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Both tx_code and tx_code_type should be present or absent");
    }
    if (suggestionArea == SuggestionArea.ClassActivity) {
      if (classId == null || caId == null || collectionId == null) {
        throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
            "ClassId, caId, collectionId should be present for Classactivity suggestion");
      }
    }
  }

  private static AddSuggestionsCommand buildFromJsonObject(JsonObject input) {
    AddSuggestionsCommand command = new AddSuggestionsCommand();

    try {
      command.userId = ConverterUtils.convertToUuid(input, CommandAttributes.USER_ID);
      command.courseId = ConverterUtils.convertToUuid(input, CommandAttributes.COURSE_ID);
      command.unitId = ConverterUtils.convertToUuid(input, CommandAttributes.UNIT_ID);
      command.lessonId = ConverterUtils.convertToUuid(input, CommandAttributes.LESSON_ID);
      command.classId = ConverterUtils.convertToUuid(input, CommandAttributes.CLASS_ID);
      command.collectionId = ConverterUtils.convertToUuid(input, CommandAttributes.COLLECTION_ID);
      command.caId = ConverterUtils.convertToLong(input, CommandAttributes.CA_ID);
      command.suggestedContentId =
          ConverterUtils.convertToUuid(input, CommandAttributes.SUGGESTED_CONTENT_ID);
      command.suggestionOrigin =
          SuggestionOrigin.builder(input.getString(CommandAttributes.SUGGESTION_ORIGIN));
      command.suggestionOriginatorId =
          ConverterUtils.convertToUuid(input, CommandAttributes.SUGGESTION_ORIGINATOR_ID);
      command.suggestionCriteria =
          SuggestionCriteria.builder(input.getString(CommandAttributes.SUGGESTION_CRITERIA));
      command.suggestedContentType =
          SuggestedContentType.builder(input.getString(CommandAttributes.SUGGESTED_CONTENT_TYPE));
      command.suggestedTo = SuggestedTo.builder(input.getString(CommandAttributes.SUGGESTED_TO));
      command.suggestionArea =
          SuggestionArea.builder(input.getString(CommandAttributes.SUGGESTION_AREA));
      command.txCode = input.getString(CommandAttributes.TX_CODE);
      command.txCodeType =
          ConverterUtils.toTxCodeType(input.getString(CommandAttributes.TX_CODE_TYPE));

    } catch (IllegalArgumentException e) {
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, e.getMessage());
    }

    return command;
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

  public Long getCaId() {
    return caId;
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
    private static final String CA_ID = "ca_id";
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
    result.setCaId(caId);
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
