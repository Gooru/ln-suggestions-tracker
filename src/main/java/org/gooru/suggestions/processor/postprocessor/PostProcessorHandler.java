package org.gooru.suggestions.processor.postprocessor;

import io.vertx.core.json.JsonObject;

/**
 * @author renuka
 */
interface PostProcessorHandler {

  void handle(JsonObject requestData);

  static PostProcessorHandler buildForTeacherSuggestionsAddCommand() {
    return new PostProcessTeacherSuggestionAddHandler();
  }

}
