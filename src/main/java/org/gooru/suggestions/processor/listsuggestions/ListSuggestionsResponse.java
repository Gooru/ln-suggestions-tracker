package org.gooru.suggestions.processor.listsuggestions;

import java.util.List;

/**
 * @author ashish.
 */

public class ListSuggestionsResponse {

  private int total;
  private List<SuggestionTrackerModel> suggestions;

  public List<SuggestionTrackerModel> getSuggestions() {
    return suggestions;
  }

  public void setSuggestions(List<SuggestionTrackerModel> suggestions) {
    this.suggestions = suggestions;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

}
