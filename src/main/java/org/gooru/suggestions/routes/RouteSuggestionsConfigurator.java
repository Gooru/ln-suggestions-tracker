package org.gooru.suggestions.routes;

import org.gooru.suggestions.constants.Constants;
import org.gooru.suggestions.routes.utils.DeliveryOptionsBuilder;
import org.gooru.suggestions.routes.utils.RouteRequestUtility;
import org.gooru.suggestions.routes.utils.RouteResponseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * @author ashish on 24/2/17.
 */
public class RouteSuggestionsConfigurator implements RouteConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteSuggestionsConfigurator.class);
    private EventBus eb = null;
    private long mbusTimeout;

    @Override
    public void configureRoutes(Vertx vertx, Router router, JsonObject config) {
        eb = vertx.eventBus();
        mbusTimeout = config.getLong(Constants.EventBus.MBUS_TIMEOUT, 30L) * 1000;
        router.post(Constants.Route.API_TEACHER_SUGGESTION_ADD).handler(this::addTeacherSuggestion);
        router.get(Constants.Route.API_USER_SUGGESTIONS_FOR_COURSE).handler(this::userSuggestionsForCourse);
        router.get(Constants.Route.API_USER_SUGGESTIONS_IN_CLASS).handler(this::userSuggestionsInClass);
        router.put(Constants.Route.API_SUGGESTIONS_ACCEPTANCE).handler(this::userSuggestionAcceptance);
    }

    private void addTeacherSuggestion(RoutingContext routingContext) {
        DeliveryOptions options = DeliveryOptionsBuilder.buildWithApiVersion(routingContext).setSendTimeout(mbusTimeout)
            .addHeader(Constants.Message.MSG_OP, Constants.Message.MSG_OP_TEACHER_SUGGESTIONS_ADD);
        eb.<JsonObject>send(Constants.EventBus.MBEP_SUGGEST_TRACKER, RouteRequestUtility.getBodyForMessage(routingContext),
            options, reply -> RouteResponseUtility.responseHandler(routingContext, reply, LOGGER));
    }

    private void userSuggestionsForCourse(RoutingContext routingContext) {
        DeliveryOptions options = DeliveryOptionsBuilder.buildWithApiVersion(routingContext).setSendTimeout(mbusTimeout)
            .addHeader(Constants.Message.MSG_OP, Constants.Message.MSG_OP_USER_SUGGESTIONS_FOR_COURSE);
        eb.<JsonObject>send(Constants.EventBus.MBEP_SUGGEST_TRACKER, RouteRequestUtility.getBodyForMessage(routingContext),
            options, reply -> RouteResponseUtility.responseHandler(routingContext, reply, LOGGER));
    }

    private void userSuggestionsInClass(RoutingContext routingContext) {
        DeliveryOptions options = DeliveryOptionsBuilder.buildWithApiVersion(routingContext).setSendTimeout(mbusTimeout)
            .addHeader(Constants.Message.MSG_OP, Constants.Message.MSG_OP_USER_SUGGESTIONS_IN_CLASS);
        eb.<JsonObject>send(Constants.EventBus.MBEP_SUGGEST_TRACKER, RouteRequestUtility.getBodyForMessage(routingContext),
            options, reply -> RouteResponseUtility.responseHandler(routingContext, reply, LOGGER));
    }

    private void userSuggestionAcceptance(RoutingContext routingContext) {
        DeliveryOptions options = DeliveryOptionsBuilder.buildWithApiVersion(routingContext).setSendTimeout(mbusTimeout)
            .addHeader(Constants.Message.MSG_OP, Constants.Message.MSG_OP_SUGGESTION_ACCEPTANCE);
        eb.<JsonObject>send(Constants.EventBus.MBEP_SUGGEST_TRACKER, RouteRequestUtility.getBodyForMessage(routingContext),
            options, reply -> RouteResponseUtility.responseHandler(routingContext, reply, LOGGER));
    }

}
