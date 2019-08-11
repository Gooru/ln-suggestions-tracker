package org.gooru.suggestions.processor.tracksuggestions;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.gooru.suggestions.processor.MessageProcessor;
import org.gooru.suggestions.processor.data.EventBusMessage;
import org.gooru.suggestions.processor.utilities.jdbi.DBICreator;
import org.gooru.suggestions.responses.MessageResponse;
import org.gooru.suggestions.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish on 17/11/17.
 */
public class AddSuggestionsProcessor implements MessageProcessor {

  private final Message<JsonObject> message;
  private final Vertx vertx;
  private final Future<MessageResponse> result;
  private EventBusMessage eventBusMessage;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(AddSuggestionsProcessor.class);
  private final AddSuggestionsService addSuggestionsService =
      new AddSuggestionsService(DBICreator.getDbiForDefaultDS());

  public AddSuggestionsProcessor(Vertx vertx, Message<JsonObject> message) {
    this.vertx = vertx;
    this.message = message;
    this.result = Future.future();
  }

  @Override
  public Future<MessageResponse> process() {
    try {
      this.eventBusMessage = EventBusMessage.eventBusMessageBuilder(message);
      AddSuggestionsCommand command =
          AddSuggestionsCommand.builder(eventBusMessage.getRequestBody());
      addTeacherSuggestion(command);
    } catch (Throwable throwable) {
      LOGGER.warn("Encountered exception", throwable);
      result.fail(throwable);
    }
    return result;
  }

  private void addTeacherSuggestion(AddSuggestionsCommand command) {
    vertx.executeBlocking(future -> {
      try {
        addSuggestionsService.addTeacherSuggestion(command);
        future.complete();
      } catch (Throwable throwable) {
        LOGGER.warn("Encountered exception accepting suggestion", throwable);
        future.fail(throwable);
      }
    }, asyncResult -> {
      if (asyncResult.succeeded()) {
        result.complete(MessageResponseFactory.createNoContentResponse());
      } else {
        result.fail(asyncResult.cause());
      }
    });

  }
}
