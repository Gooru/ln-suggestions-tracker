package org.gooru.suggestions.responses.auth;

public interface AuthResponseHolder {

  boolean isAuthorized();

  boolean isAnonymous();

  String getUser();
}
