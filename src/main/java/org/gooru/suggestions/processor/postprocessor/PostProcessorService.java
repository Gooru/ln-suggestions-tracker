package org.gooru.suggestions.processor.postprocessor;

import io.vertx.core.json.JsonObject;

/**
 * @author renuka
 */
public interface PostProcessorService {

  static PostProcessorService build() {
    return new PostProcessorServiceImpl();
  }

  void process(String op, JsonObject requestData);
}
