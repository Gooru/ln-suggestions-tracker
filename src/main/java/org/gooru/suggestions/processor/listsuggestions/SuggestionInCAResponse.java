package org.gooru.suggestions.processor.listsuggestions;

import java.util.List;

/**
 * @author renuka.
 */

public class SuggestionInCAResponse {

  private Long caId;
  private int total;
  private List<SuggestionTrackerModel> suggestions;

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
  public List<SuggestionTrackerModel> getSuggestions() {
    return suggestions;
  }
  public void setSuggestions(List<SuggestionTrackerModel> suggestions) {
    this.suggestions = suggestions;
  }
  
}
