package org.gooru.suggestions.processor.listsuggestions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import java.util.List;
import org.gooru.suggestions.processor.MessageProcessor;
import org.gooru.suggestions.processor.data.EventBusMessage;
import org.gooru.suggestions.processor.utilities.jdbi.DBICreator;
import org.gooru.suggestions.responses.MessageResponse;
import org.gooru.suggestions.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish
 */
public class ListUserSuggestionsInClassProcessor implements MessageProcessor {

  private final Vertx vertx;
  private final Message<JsonObject> message;
  private final Future<MessageResponse> result;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ListUserSuggestionsInClassProcessor.class);
  private final ListUserSuggestionsService listUserSuggestionsService =
      new ListUserSuggestionsService(DBICreator.getDbiForDefaultDS());
  private EventBusMessage eventBusMessage;

  public ListUserSuggestionsInClassProcessor(Vertx vertx, Message<JsonObject> message) {
    this.vertx = vertx;
    this.message = message;
    result = Future.future();
  }

  @Override
  public Future<MessageResponse> process() {
    try {
      this.eventBusMessage = EventBusMessage.eventBusMessageBuilder(message);
      ListUserSuggestionsInClassCommand command =
          ListUserSuggestionsInClassCommand.builder(eventBusMessage.getRequestBody());
      fetchUserSuggestionInClass(command);
    } catch (Throwable throwable) {
      LOGGER.warn("Encountered exception", throwable);
      result.fail(throwable);
    }
    return result;
  }

  private void fetchUserSuggestionInClass(ListUserSuggestionsInClassCommand command) {
    JsonObject response = listUserSuggestionsService.fetchSuggestionsInClass(command);
    result.complete(MessageResponseFactory.createOkayResponse(response));
  }

}
