package org.gooru.suggestions.processor;

import org.gooru.suggestions.processor.suggestionsacceptance.SuggestionAcceptanceProcessor;
import org.gooru.suggestions.processor.teachersuggestions.AddTeacherSuggestionsProcessor;
import org.gooru.suggestions.processor.usersuggestions.UserSuggestionsForCourseProcessor;
import org.gooru.suggestions.processor.usersuggestions.UserSuggestionsInClassProcessor;
import org.gooru.suggestions.responses.MessageResponse;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish on 17/11/17.
 */
public interface MessageProcessor {

    Future<MessageResponse> process();

    static MessageProcessor buildAddTeacherSuggestionsProcessor(Vertx vertx, Message<JsonObject> message) {
        return new AddTeacherSuggestionsProcessor(vertx, message);
    }

    static MessageProcessor buildSuggestionAcceptanceProcessor(Vertx vertx, Message<JsonObject> message) {
        return new SuggestionAcceptanceProcessor(vertx, message);
    }

    static MessageProcessor buildUserSuggestionsForCourseProcessor(Vertx vertx, Message<JsonObject> message) {
        return new UserSuggestionsForCourseProcessor(vertx, message);
    }

    static MessageProcessor buildUserSuggestionsInClassProcessor(Vertx vertx, Message<JsonObject> message) {
        return new UserSuggestionsInClassProcessor(vertx, message);
    }

    static MessageProcessor buildStubbedProcessor(Vertx vertx, Message<JsonObject> message) {
        return new StubbedMessageProcessor(vertx, message);
    }
}
