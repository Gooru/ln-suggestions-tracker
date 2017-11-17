package org.gooru.suggestions.responses.auth;

import io.vertx.core.json.JsonObject;

/**
 * @author ashish on 3/11/17.
 */
public interface AuthSessionResponseHolder extends AuthResponseHolder {
    JsonObject getSession();
}
