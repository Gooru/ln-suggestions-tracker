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
public class ListUserSuggestionsForCourseProcessor implements MessageProcessor {

  private final Vertx vertx;
  private final Message<JsonObject> message;
  private final Future<MessageResponse> result;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ListUserSuggestionsForCourseProcessor.class);
  private final ListUserSuggestionsService listUserSuggestionsService =
      new ListUserSuggestionsService(DBICreator.getDbiForDefaultDS());
  private EventBusMessage eventBusMessage;

  public ListUserSuggestionsForCourseProcessor(Vertx vertx, Message<JsonObject> message) {
    this.vertx = vertx;
    this.message = message;
    result = Future.future();
  }

  @Override
  public Future<MessageResponse> process() {
    try {
      this.eventBusMessage = EventBusMessage.eventBusMessageBuilder(message);
      ListUserSuggestionsForCourseCommand command =
          ListUserSuggestionsForCourseCommand.builder(eventBusMessage.getRequestBody());
      fetchUserSuggestionForCourse(command);
    } catch (Throwable throwable) {
      LOGGER.warn("Encountered exception", throwable);
      result.fail(throwable);
    }
    return result;
  }

  private void fetchUserSuggestionForCourse(ListUserSuggestionsForCourseCommand command) {
    try {
      List<SuggestionTrackerModel> models = listUserSuggestionsService
          .fetchSuggestionsForCourse(command);
      SuggestionsList outcome = new SuggestionsList();
      outcome.setSuggestions(models);
      String resultString = new ObjectMapper().writeValueAsString(outcome);
      result.complete(MessageResponseFactory.createOkayResponse(new JsonObject(resultString)));
    } catch (JsonProcessingException e) {
      LOGGER.error("Not able to convert data to JSON", e);
      result.fail(e);
    } catch (DecodeException e) {
      LOGGER.warn("Not able to convert data to JSON", e);
      result.fail(e);
    } catch (Throwable throwable) {
      LOGGER.warn("Encountered exception", throwable);
      result.fail(throwable);
    }
  }

  private static final class SuggestionsList {

    List<SuggestionTrackerModel> suggestions;

    public List<SuggestionTrackerModel> getSuggestions() {
      return suggestions;
    }

    public void setSuggestions(List<SuggestionTrackerModel> suggestions) {
      this.suggestions = suggestions;
    }
  }

}
