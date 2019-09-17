package org.gooru.suggestions.processor.postprocessor;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;

/**
 * @author renuka
 */
class PostProcessTeacherSuggestionAddHandler implements PostProcessorHandler {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(PostProcessTeacherSuggestionAddHandler.class);
  private TeacherSuggestionPayload command;

  @Override
  public void handle(JsonObject requestData) {
    LOGGER.info("Processing teacher suggestion accept handler for payload: '{}'", requestData);
    initialize(requestData);
    if (command.getUserId() != null) {
      NotificationCoordinator.buildForTeacherSuggestionAdded(command)
          .coordinateNotification();
    } else {
      LOGGER.warn("User id list is null or empty for teacher suggestions.");
    }

  }

  private void initialize(JsonObject requestData) {
    command = buildFromJson(requestData);
  }

  private TeacherSuggestionPayload buildFromJson(JsonObject request) {
    return request.mapTo(TeacherSuggestionPayload.class);
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TeacherSuggestionPayload {

    @JsonProperty("user_id")
    private UUID userId;
    @JsonProperty("class_id")
    private UUID classId;
    @JsonProperty("course_id")
    private UUID courseId;
    @JsonProperty("unit_id")
    private UUID unitId;
    @JsonProperty("lesson_id")
    private UUID lessonId;
    @JsonProperty("collection_id")
    private UUID collectionId;
    @JsonProperty("ca_id")
    private Long caId;
    @JsonProperty("suggested_content_id")
    private UUID suggestedContentId;
    @JsonProperty("suggested_content_type")
    private String suggestedContentType;
    @JsonProperty("teacher_id")
    private UUID teacherId;
    @JsonProperty("suggestion_area")
    private String suggestionArea;
    @JsonProperty("id")
    private Long pathId;
    @JsonProperty("tx_code")
    private String txCode;
    @JsonProperty("tx_code_type")
    private String txCodeType;

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

    public UUID getCourseId() {
      return courseId;
    }

    public void setCourseId(UUID courseId) {
      this.courseId = courseId;
    }

    public UUID getUnitId() {
      return unitId;
    }

    public void setUnitId(UUID unitId) {
      this.unitId = unitId;
    }

    public UUID getLessonId() {
      return lessonId;
    }

    public void setLessonId(UUID lessonId) {
      this.lessonId = lessonId;
    }

    public UUID getCollectionId() {
      return collectionId;
    }

    public void setCollectionId(UUID collectionId) {
      this.collectionId = collectionId;
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

    public UUID getTeacherId() {
      return teacherId;
    }

    public void setTeacherId(UUID teacherId) {
      this.teacherId = teacherId;
    }

    public Long getCaId() {
      return caId;
    }

    public void setCaId(Long caId) {
      this.caId = caId;
    }

    public String getSuggestionArea() {
      return suggestionArea;
    }

    public void setSuggestionArea(String suggestionArea) {
      this.suggestionArea = suggestionArea;
    }

    public Long getPathId() {
      return pathId;
    }

    public void setPathId(Long pathId) {
      this.pathId = pathId;
    }

    public String getTxCode() {
      return txCode;
    }

    public void setTxCode(String txCode) {
      this.txCode = txCode;
    }

    public String getTxCodeType() {
      return txCodeType;
    }

    public void setTxCodeType(String txCodeType) {
      this.txCodeType = txCodeType;
    }

  }

}
