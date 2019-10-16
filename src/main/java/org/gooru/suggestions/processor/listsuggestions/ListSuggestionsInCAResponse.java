package org.gooru.suggestions.processor.listsuggestions;

import java.util.Date;
import java.util.List;

/**
 * @author renuka.
 */

public class ListSuggestionsInCAResponse {

  private List<Suggestions> suggestions;

  public List<Suggestions> getSuggestions() {
    return suggestions;
  }

  public void setSuggestions(List<Suggestions> suggestions) {
    this.suggestions = suggestions;
  }

  static class Suggestions {
    private Long caId;
    private int total;
    private List<SuggestedContents> suggestedContents;

    public Long getCaId() {
      return caId;
    }

    public void setCaId(Long caId) {
      this.caId = caId;
    }

    public int getTotal() {
      return total;
    }

    public void setTotal(int total) {
      this.total = total;
    }

    public List<SuggestedContents> getSuggestedContents() {
      return suggestedContents;
    }

    public void setSuggestedContents(List<SuggestedContents> suggestions) {
      this.suggestedContents = suggestions;
    }

  }

  static class SuggestedContents {
    private String suggestedContentId;
    private String suggestedContentType;
    private String classId;
    private String collectionId;
    private List<SuggestedToContext> suggestedToContext;
    private Integer userCount;

    public String getSuggestedContentId() {
      return suggestedContentId;
    }

    public void setSuggestedContentId(String suggestedContentId) {
      this.suggestedContentId = suggestedContentId;
    }

    public String getSuggestedContentType() {
      return suggestedContentType;
    }

    public void setSuggestedContentType(String suggestedContentType) {
      this.suggestedContentType = suggestedContentType;
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

    public List<SuggestedToContext> getSuggestedToContext() {
      return suggestedToContext;
    }

    public void setSuggestedToContext(List<SuggestedToContext> suggestedToContext) {
      this.suggestedToContext = suggestedToContext;
    }

    public Integer getUserCount() {
      return userCount;
    }

    public void setUserCount(Integer userCount) {
      this.userCount = userCount;
    }

  }

  static class SuggestedToContext {
    private Long id;
    private String userId;
    private String suggestionOrigin;
    private String suggestionOriginatorId;
    private String suggestionCriteria;
    private String suggestedTo;
    private String suggestionArea;
    private Date createdAt;
    private Date updatedAt;

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

    public String getSuggestedTo() {
      return suggestedTo;
    }

    public void setSuggestedTo(String suggestedTo) {
      this.suggestedTo = suggestedTo;
    }

    public String getSuggestionArea() {
      return suggestionArea;
    }

    public void setSuggestionArea(String suggestionArea) {
      this.suggestionArea = suggestionArea;
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

}
