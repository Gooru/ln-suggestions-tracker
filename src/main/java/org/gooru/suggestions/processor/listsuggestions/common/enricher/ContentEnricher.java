package org.gooru.suggestions.processor.listsuggestions.common.enricher;

import org.gooru.suggestions.processor.listsuggestions.ListSuggestionsInCAResponse;
import org.gooru.suggestions.processor.listsuggestions.ListSuggestionsResponse;
import io.vertx.core.json.JsonObject;

public interface ContentEnricher {

  JsonObject enrichContent();

  static ContentEnricher buildContentEnricherForSuggestions(
      ListSuggestionsResponse classContents) {

    return new ContentEnricherForAllTypesOfActivities<Object>(classContents, false);
  }

  static ContentEnricher buildContentEnricherForTxCodeSuggestions(
      ListSuggestionsResponse classContents) {

    return new ContentEnricherForAllTypesOfActivities<Object>(classContents, false);
  }

  static ContentEnricher buildContentEnricherForCASuggestions(
      ListSuggestionsInCAResponse classContents) {

    return new ContentEnricherForAllTypesOfActivities<Object>(classContents, true);
  }
}
