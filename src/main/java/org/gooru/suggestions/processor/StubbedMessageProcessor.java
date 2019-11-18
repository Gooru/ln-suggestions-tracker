package org.gooru.suggestions.processor;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.gooru.suggestions.responses.MessageResponse;
import org.gooru.suggestions.responses.MessageResponseFactory;

/**
 * @author ashish
 */
public class StubbedMessageProcessor implements MessageProcessor {

  private final Vertx vertx;
  private final Message<JsonObject> message;

  public StubbedMessageProcessor(Vertx vertx, Message<JsonObject> message) {
    this.vertx = vertx;
    this.message = message;
  }

  @Override
  public Future<MessageResponse> process() {
    Future<MessageResponse> result = Future.future();
    result.complete(
        MessageResponseFactory
            .createOkayResponse(new JsonObject().put("message", "This is stubbed response")));
    return result;
  }
}
