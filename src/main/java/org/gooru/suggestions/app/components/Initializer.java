package org.gooru.suggestions.app.components;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish on 3/11/17.
 */
public interface Initializer {

  void initializeComponent(Vertx vertx, JsonObject config);

}
