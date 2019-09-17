package org.gooru.suggestions.processor.postprocessor;

import io.vertx.core.json.JsonObject;

interface PostProcessorHandler {

  void handle(JsonObject requestData);

  static PostProcessorHandler buildForSuggestionsAddCommand() {
    return new PostProcessSuggestionAddHandler();
  }

}
