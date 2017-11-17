package org.gooru.suggestions.bootstrap.verticles;

import org.gooru.suggestions.constants.Constants;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.exceptions.MessageResponseWrapperException;
import org.gooru.suggestions.responses.MessageResponse;
import org.gooru.suggestions.responses.MessageResponseFactory;
import org.gooru.suggestions.responses.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish on 17/11/17.
 */
public class SuggestionsTrackerVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionsTrackerVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        EventBus eb = vertx.eventBus();
        eb.localConsumer(Constants.EventBus.MBEP_SUGGEST_TRACKER, this::processMessage).completionHandler(result -> {
            if (result.succeeded()) {
                LOGGER.info("Tracker end point ready to listen");
                startFuture.complete();
            } else {
                LOGGER.error("Error registering the Tracker handler. Halting the machinery");
                startFuture.fail(result.cause());
                Runtime.getRuntime().halt(1);
            }
        });
    }

    private void processMessage(Message<JsonObject> message) {
        String op = message.headers().get(Constants.Message.MSG_OP);
        switch (op) {
        case Constants.Message.MSG_OP_SUGGESTION_ACCEPTANCE:
            dummyProcess().setHandler(event -> finishResponse(message, event));
            break;
        case Constants.Message.MSG_OP_TEACHER_SUGGESTIONS_ADD:
            dummyProcess().setHandler(event -> finishResponse(message, event));
            break;
        case Constants.Message.MSG_OP_USER_SUGGESTIONS_FOR_COURSE:
            dummyProcess().setHandler(event -> finishResponse(message, event));
            break;
        case Constants.Message.MSG_OP_USER_SUGGESTIONS_IN_CLASS:
            dummyProcess().setHandler(event -> finishResponse(message, event));
            break;
        default:
            LOGGER.warn("Invalid operation type");
            ResponseUtil.processFailure(message);
        }
    }

    private void finishResponse(Message<JsonObject> message, AsyncResult<MessageResponse> asyncResult) {
        if (asyncResult.succeeded()) {
            message.reply(asyncResult.result().reply(), asyncResult.result().deliveryOptions());
        } else {
            LOGGER.warn("Failed to process command", asyncResult.cause());
            if (asyncResult.cause() instanceof HttpResponseWrapperException) {
                HttpResponseWrapperException exception = (HttpResponseWrapperException) asyncResult.cause();
                message.reply(new JsonObject().put(Constants.Message.MSG_HTTP_STATUS, exception.getStatus())
                    .put(Constants.Message.MSG_HTTP_BODY, exception.getBody())
                    .put(Constants.Message.MSG_HTTP_HEADERS, new JsonObject()));
            } else if (asyncResult.cause() instanceof MessageResponseWrapperException) {
                MessageResponseWrapperException exception = (MessageResponseWrapperException) asyncResult.cause();
                message.reply(exception.getMessageResponse().reply(), exception.getMessageResponse().deliveryOptions());
            } else {
                message.reply(new JsonObject().put(Constants.Message.MSG_HTTP_STATUS, 500)
                    .put(Constants.Message.MSG_HTTP_BODY, new JsonObject())
                    .put(Constants.Message.MSG_HTTP_HEADERS, new JsonObject()));
            }
        }
    }

    private Future<MessageResponse> dummyProcess() {
        Future<MessageResponse> result = Future.future();
        result.complete(MessageResponseFactory
            .createOkayResponse(new JsonObject().put("message", "This is stubbed " + "response")));
        return result;
    }

}
