package org.gooru.suggestions.processor.usersuggestions;

import java.util.Date;
import java.util.UUID;

/**
 * @author ashish on 24/11/17.
 */
class SuggestionTrackerModel {

  private Long id;
  private UUID ctxUserId;
  private UUID ctxClassId;
  private UUID ctxCourseId;
  private UUID ctxUnitId;
  private UUID ctxLessonId;
  private UUID ctxCollectionId;
  private Long pathId;
  private UUID suggestedContentId;
  private String suggestedContentType;
  private String suggestedContentSubType;
  private Boolean acceptedByUser;
  private String suggestionType;
  private Date acceptedAt;
  private Date createdAt;
  private Date updatedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Long getPathId() {
    return pathId;
  }

  public void setPathId(Long pathId) {
    this.pathId = pathId;
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

  public Boolean getAcceptedByUser() {
    return acceptedByUser;
  }

  public void setAcceptedByUser(Boolean acceptedByUser) {
    this.acceptedByUser = acceptedByUser;
  }

  public String getSuggestionType() {
    return suggestionType;
  }

  public void setSuggestionType(String suggestionType) {
    this.suggestionType = suggestionType;
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

}
