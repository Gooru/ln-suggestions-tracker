package org.gooru.suggestions.routes;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * @author ashish on 3/11/17.
 */
public interface RouteConfigurator {
    void configureRoutes(Vertx vertx, Router router, JsonObject config);
}
