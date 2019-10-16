package org.gooru.suggestions.processor.listsuggestions;

import java.util.Date;

/**
 * @author ashish
 */
class SuggestionTrackerModel {

  private Long id;
  private String userId;
  private String courseId;
  private String unitId;
  private String lessonId;
  private String classId;
  private String collectionId;
  private String suggestedContentId;
  private String suggestionOrigin;
  private String suggestionOriginatorId;
  private String suggestionCriteria;
  private String suggestedContentType;
  private String suggestedTo;
  private Boolean accepted;
  private Date acceptedAt;
  private Date createdAt;
  private Date updatedAt;
  private String suggestionArea;
  private String txCode;
  private String txCodeType;
  private Long pathId;
  private Long caId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getUnitId() {
    return unitId;
  }

  public void setUnitId(String unitId) {
    this.unitId = unitId;
  }

  public String getLessonId() {
    return lessonId;
  }

  public void setLessonId(String lessonId) {
    this.lessonId = lessonId;
  }

  public String getClassId() {
    return classId;
  }

  public void setClassId(String classId) {
    this.classId = classId;
  }

  public String getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  public String getSuggestedContentId() {
    return suggestedContentId;
  }

  public void setSuggestedContentId(String suggestedContentId) {
    this.suggestedContentId = suggestedContentId;
  }

  public String getSuggestionOrigin() {
    return suggestionOrigin;
  }

  public void setSuggestionOrigin(String suggestionOrigin) {
    this.suggestionOrigin = suggestionOrigin;
  }

  public String getSuggestionOriginatorId() {
    return suggestionOriginatorId;
  }

  public void setSuggestionOriginatorId(String suggestionOriginatorId) {
    this.suggestionOriginatorId = suggestionOriginatorId;
  }

  public String getSuggestionCriteria() {
    return suggestionCriteria;
  }

  public void setSuggestionCriteria(String suggestionCriteria) {
    this.suggestionCriteria = suggestionCriteria;
  }

  public String getSuggestedContentType() {
    return suggestedContentType;
  }

  public void setSuggestedContentType(String suggestedContentType) {
    this.suggestedContentType = suggestedContentType;
  }

  public String getSuggestedTo() {
    return suggestedTo;
  }

  public void setSuggestedTo(String suggestedTo) {
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

  public String getSuggestionArea() {
    return suggestionArea;
  }

  public void setSuggestionArea(String suggestionArea) {
    this.suggestionArea = suggestionArea;
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

  public Long getPathId() {
    return pathId;
  }

  public void setPathId(Long pathId) {
    this.pathId = pathId;
  }

  public Long getCaId() {
    return caId;
  }

  public void setCaId(Long caId) {
    this.caId = caId;
  }
}
