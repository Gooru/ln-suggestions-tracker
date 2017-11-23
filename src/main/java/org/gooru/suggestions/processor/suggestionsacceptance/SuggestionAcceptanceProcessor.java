package org.gooru.suggestions.processor.suggestionsacceptance;

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
 * @author ashish on 17/11/17.
 */
public class SuggestionAcceptanceProcessor implements MessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionAcceptanceProcessor.class);

    private final Message<JsonObject> message;
    private final Vertx vertx;
    private final Future<MessageResponse> result;
    private final SuggestionAcceptanceService suggestionAcceptanceService =
        new SuggestionAcceptanceService(DBICreator.getDbiForDefaultDS());
    private EventBusMessage eventBusMessage;

    public SuggestionAcceptanceProcessor(Vertx vertx, Message<JsonObject> message) {
        this.vertx = vertx;
        this.message = message;
        this.result = Future.future();
    }

    @Override
    public Future<MessageResponse> process() {
        try {
            this.eventBusMessage = EventBusMessage.eventBusMessageBuilder(message);
            SuggestionAcceptanceCommand command = SuggestionAcceptanceCommand.builder(eventBusMessage.getRequestBody());
            acceptSuggestion(command);
        } catch (Throwable throwable) {
            LOGGER.warn("Encountered exception", throwable);
            result.fail(throwable);
        }
        return result;
    }

    private void acceptSuggestion(SuggestionAcceptanceCommand command) {
        vertx.executeBlocking(future -> {
            try {
                suggestionAcceptanceService.acceptSuggestion(command);
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
