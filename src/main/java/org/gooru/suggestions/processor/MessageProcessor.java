package org.gooru.suggestions.processor;

import org.gooru.suggestions.processor.listsuggestions.ListUserSuggestionsForTxCodeProcessor;
import org.gooru.suggestions.processor.listsuggestions.ListUserSuggestionsForCourseProcessor;
import org.gooru.suggestions.processor.listsuggestions.ListUserSuggestionsInCAProcessor;
import org.gooru.suggestions.processor.listsuggestions.ListUserSuggestionsInClassProcessor;
import org.gooru.suggestions.processor.tracksuggestions.AddSuggestionsProcessor;
import org.gooru.suggestions.responses.MessageResponse;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish
 */
public interface MessageProcessor {

  Future<MessageResponse> process();

  static MessageProcessor buildAddSuggestionsProcessor(Vertx vertx,
      Message<JsonObject> message) {
    return new AddSuggestionsProcessor(vertx, message);
  }

  static MessageProcessor buildUserSuggestionsForCourseProcessor(Vertx vertx,
      Message<JsonObject> message) {
    return new ListUserSuggestionsForCourseProcessor(vertx, message);
  }

  static MessageProcessor buildUserSuggestionsInClassProcessor(Vertx vertx,
      Message<JsonObject> message) {
    return new ListUserSuggestionsInClassProcessor(vertx, message);
  }

  static MessageProcessor buildStubbedProcessor(Vertx vertx, Message<JsonObject> message) {
    return new StubbedMessageProcessor(vertx, message);
  }
  
  static MessageProcessor buildUserSuggestionsForTxCodeProcessor(Vertx vertx,
      Message<JsonObject> message) {
    return new ListUserSuggestionsForTxCodeProcessor(vertx, message);
  }
  
  static MessageProcessor buildUserSuggestionsForCAProcessor(Vertx vertx,
      Message<JsonObject> message) {
    return new ListUserSuggestionsInCAProcessor(vertx, message);
  }
}
