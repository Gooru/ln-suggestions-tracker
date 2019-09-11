package org.gooru.suggestions.processor.listsuggestions;

import java.util.Date;
import java.util.UUID;
import org.gooru.suggestions.processor.data.SuggestedContentType;
import org.gooru.suggestions.processor.data.SuggestedTo;
import org.gooru.suggestions.processor.data.SuggestionArea;
import org.gooru.suggestions.processor.data.SuggestionCriteria;
import org.gooru.suggestions.processor.data.SuggestionOrigin;
import org.gooru.suggestions.processor.data.TxCodeType;

/**
 * @author ashish
 */
class SuggestionTrackerModel {

  private Long id;
  private UUID userId;
  private UUID courseId;
  private UUID unitId;
  private UUID lessonId;
  private UUID classId;
  private UUID collectionId;
  private UUID suggestedContentId;
  private SuggestionOrigin suggestionOrigin;
  private UUID suggestionOriginatorId;
  private SuggestionCriteria suggestionCriteria;
  private SuggestedContentType suggestedContentType;
  private SuggestedTo suggestedTo;
  private Boolean accepted;
  private Date acceptedAt;
  private Date createdAt;
  private Date updatedAt;
  private SuggestionArea suggestionArea;
  private String txCode;
  private TxCodeType txCodeType;
  private Long pathId;
  private Long caContentId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
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

  public UUID getSuggestedContentId() {
    return suggestedContentId;
  }

  public void setSuggestedContentId(UUID suggestedContentId) {
    this.suggestedContentId = suggestedContentId;
  }

  public SuggestionOrigin getSuggestionOrigin() {
    return suggestionOrigin;
  }

  public void setSuggestionOrigin(SuggestionOrigin suggestionOrigin) {
    this.suggestionOrigin = suggestionOrigin;
  }

  public UUID getSuggestionOriginatorId() {
    return suggestionOriginatorId;
  }

  public void setSuggestionOriginatorId(UUID suggestionOriginatorId) {
    this.suggestionOriginatorId = suggestionOriginatorId;
  }

  public SuggestionCriteria getSuggestionCriteria() {
    return suggestionCriteria;
  }

  public void setSuggestionCriteria(
      SuggestionCriteria suggestionCriteria) {
    this.suggestionCriteria = suggestionCriteria;
  }

  public SuggestedContentType getSuggestedContentType() {
    return suggestedContentType;
  }

  public void setSuggestedContentType(
      SuggestedContentType suggestedContentType) {
    this.suggestedContentType = suggestedContentType;
  }

  public SuggestedTo getSuggestedTo() {
    return suggestedTo;
  }

  public void setSuggestedTo(SuggestedTo suggestedTo) {
    this.suggestedTo = suggestedTo;
  }

  public Boolean getAccepted() {
    return accepted;
  }

  public void setAccepted(Boolean accepted) {
    this.accepted = accepted;
  }

  public Date getAcceptedAt() {
    return acceptedAt;
  }

  public void setAcceptedAt(Date acceptedAt) {
    this.acceptedAt = acceptedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public SuggestionArea getSuggestionArea() {
    return suggestionArea;
  }

  public void setSuggestionArea(SuggestionArea suggestionArea) {
    this.suggestionArea = suggestionArea;
  }

  public String getTxCode() {
    return txCode;
  }

  public void setTxCode(String txCode) {
    this.txCode = txCode;
  }

  public TxCodeType getTxCodeType() {
    return txCodeType;
  }

  public void setTxCodeType(TxCodeType txCodeType) {
    this.txCodeType = txCodeType;
  }

  public Long getPathId() {
    return pathId;
  }

  public void setPathId(Long pathId) {
    this.pathId = pathId;
  }

  public Long getCaContentId() {
    return caContentId;
  }

  public void setCaContentId(Long caContentId) {
    this.caContentId = caContentId;
  }
}
