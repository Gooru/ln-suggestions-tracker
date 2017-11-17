package org.gooru.suggestions.processor.suggestionsacceptance;

import org.gooru.suggestions.processor.MessageProcessor;
import org.gooru.suggestions.responses.MessageResponse;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish on 17/11/17.
 */
public class SuggestionAcceptanceProcessor implements MessageProcessor {

    private final Message<JsonObject> message;
    private final Vertx vertx;

    public SuggestionAcceptanceProcessor(Vertx vertx, Message<JsonObject> message) {
        this.vertx = vertx;
        this.message = message;
    }

    @Override
    public Future<MessageResponse> process() {
        return null;
    }
}
