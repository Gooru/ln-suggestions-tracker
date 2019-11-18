package org.gooru.suggestions.processor.listsuggestions;

import org.gooru.suggestions.processor.MessageProcessor;
import org.gooru.suggestions.processor.data.EventBusMessage;
import org.gooru.suggestions.processor.utilities.jdbi.DBICreator;
import org.gooru.suggestions.responses.MessageResponse;
import org.gooru.suggestions.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;

/**
 * @author renuka
 */
public class ListUserSuggestionsInCAProcessor implements MessageProcessor {

  private final Vertx vertx;
  private final Message<JsonObject> message;
  private final Future<MessageResponse> result;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ListUserSuggestionsInCAProcessor.class);
  private final ListUserSuggestionsInCAService listUserSuggestionsService =
      new ListUserSuggestionsInCAService(DBICreator.getDbiForDefaultDS());
  private EventBusMessage eventBusMessage;

  public ListUserSuggestionsInCAProcessor(Vertx vertx, Message<JsonObject> message) {
    this.vertx = vertx;
    this.message = message;
    result = Future.future();
  }

  @Override
  public Future<MessageResponse> process() {
    try {
      this.eventBusMessage = EventBusMessage.eventBusMessageBuilder(message);
      ListUserSuggestionsInCACommand command =
          ListUserSuggestionsInCACommand.builder(eventBusMessage.getRequestBody());
      fetchUserSuggestionForCA(command);
    } catch (Throwable throwable) {
      LOGGER.warn("Encountered exception", throwable);
      result.fail(throwable);
    }
    return result;
  }

  private void fetchUserSuggestionForCA(ListUserSuggestionsInCACommand command) {
    try {
      JsonObject response =
          listUserSuggestionsService.fetchSuggestionsInCA(command, eventBusMessage.getUserId());
      result.complete(MessageResponseFactory.createOkayResponse(response));
    } catch (Throwable throwable) {
      LOGGER.warn("Encountered exception", throwable);
      result.fail(throwable);
    }
  }

}
