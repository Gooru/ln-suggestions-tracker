package org.gooru.suggestions.processor.listsuggestions;

import java.util.List;

/**
 * @author renuka.
 */

public class ListSuggestionsInCAResponse {

  private List<SuggestionInCAResponse> suggestions;

  public List<SuggestionInCAResponse> getSuggestions() {
    return suggestions;
  }

  public void setSuggestions(List<SuggestionInCAResponse> suggestions) {
    this.suggestions = suggestions;
  }

}
