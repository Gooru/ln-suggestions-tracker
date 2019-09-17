package org.gooru.suggestions.processor.tracksuggestions;

import java.util.UUID;
import org.gooru.suggestions.constants.Constants;
import org.gooru.suggestions.processor.MessageProcessor;
import org.gooru.suggestions.processor.data.EventBusMessage;
import org.gooru.suggestions.processor.data.SuggestionArea;
import org.gooru.suggestions.processor.utilities.jdbi.DBICreator;
import org.gooru.suggestions.responses.MessageResponse;
import org.gooru.suggestions.responses.MessageResponseFactory;
import org.gooru.suggestions.routes.utils.DeliveryOptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish
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
        int result = addSuggestionsService.addSuggestion(command);
        if ((command.getSuggestionArea() == SuggestionArea.ClassActivity
            || command.getSuggestionArea() == SuggestionArea.Proficiency) && result > 0) {
          JsonObject postProcessorPayload = createPostProcessorPayload(result);
          future.complete(postProcessorPayload);
        } else {
          future.complete();
        }
      } catch (Throwable throwable) {
        LOGGER.warn("Encountered exception accepting suggestion", throwable);
        future.fail(throwable);
      }
    }, asyncResult -> {
      if (asyncResult.succeeded()) {
        if (asyncResult.result() != null) {
          vertx.eventBus().send(Constants.EventBus.MBEP_POST_PROCESS, asyncResult.result(),
              DeliveryOptionsBuilder.createDeliveryOptionsWithMsgOp(
                  Constants.Message.MSG_OP_POSTPROCESS_TEACHER_SUGGESTION_ADD));
        }
        result.complete(MessageResponseFactory.createNoContentResponse());
      } else {
        result.fail(asyncResult.cause());
      }
    });

  }
    private JsonObject createPostProcessorPayload(int result) {
    JsonObject postProcessorPayload = eventBusMessage.getRequestBody().copy();
    UUID teacherId = eventBusMessage.getUserId();
    return postProcessorPayload.put("teacher_id", teacherId.toString())
        .put("id", result);
  }
}
