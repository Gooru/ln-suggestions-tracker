package org.gooru.suggestions.processor.postprocessor;

import org.gooru.suggestions.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.json.JsonObject;

class PostProcessorServiceImpl implements PostProcessorService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PostProcessorService.class);

  @Override
  public void process(String op, JsonObject requestData) {
    switch (op) {
      case Constants.Message.MSG_OP_POSTPROCESS_SUGGESTION_ADD:
        PostProcessorHandler.buildForSuggestionsAddCommand().handle(requestData);
        break;
      default:
        LOGGER.warn("Invalid op: '{}'", op);
    }
  }

}
