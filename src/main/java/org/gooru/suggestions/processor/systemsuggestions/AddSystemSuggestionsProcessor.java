package org.gooru.suggestions.processor.systemsuggestions;

import org.gooru.suggestions.processor.MessageProcessor;
import org.gooru.suggestions.processor.data.EventBusMessage;
import org.gooru.suggestions.processor.utilities.jdbi.DBICreator;
import org.gooru.suggestions.responses.MessageResponse;
import org.gooru.suggestions.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish on 29/11/17.
 */
public class AddSystemSuggestionsProcessor implements MessageProcessor {
    private final Vertx vertx;
    private final Message<JsonObject> message;
    private final Future<MessageResponse> result;
    private EventBusMessage eventBusMessage;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddSystemSuggestionsProcessor.class);
    private final AddSystemSuggestionsService addSystemSuggestionsService =
        new AddSystemSuggestionsService(DBICreator.getDbiForDefaultDS());

    public AddSystemSuggestionsProcessor(Vertx vertx, Message<JsonObject> message) {

        this.vertx = vertx;
        this.message = message;
        this.result = Future.future();
    }

    @Override
    public Future<MessageResponse> process() {
        try {
            this.eventBusMessage = EventBusMessage.eventBusMessageBuilder(message);
            AddSystemSuggestionsCommand command = AddSystemSuggestionsCommand.builder(eventBusMessage.getRequestBody());
            addSystemSuggestion(command);
        } catch (Throwable throwable) {
            LOGGER.warn("Encountered exception", throwable);
            result.fail(throwable);
        }
        return result;
    }

    private void addSystemSuggestion(AddSystemSuggestionsCommand command) {
        vertx.executeBlocking(future -> {
            try {
                addSystemSuggestionsService.addSystemSuggestion(command);
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
