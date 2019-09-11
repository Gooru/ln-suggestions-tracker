package org.gooru.suggestions.processor.tracksuggestions;

import java.util.Date;
import java.util.UUID;

/**
 * @author ashish.
 */

public class AddSuggestionBean {

  private UUID userId;
  private UUID courseId;
  private UUID unitId;
  private UUID lessonId;
  private UUID classId;
  private UUID collectionId;
  private Long caContentId;
  private UUID suggestedContentId;
  private String suggestionOrigin;
  private UUID suggestionOriginatorId;
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

  public Long getCaContentId() {
    return caContentId;
  }

  public void setCaContentId(Long caContentId) {
    this.caContentId = caContentId;
  }

  public UUID getSuggestedContentId() {
    return suggestedContentId;
  }

  public void setSuggestedContentId(UUID suggestedContentId) {
    this.suggestedContentId = suggestedContentId;
  }

  public String getSuggestionOrigin() {
    return suggestionOrigin;
  }

  public void setSuggestionOrigin(String suggestionOrigin) {
    this.suggestionOrigin = suggestionOrigin;
  }

  public UUID getSuggestionOriginatorId() {
    return suggestionOriginatorId;
  }

  public void setSuggestionOriginatorId(UUID suggestionOriginatorId) {
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

}
