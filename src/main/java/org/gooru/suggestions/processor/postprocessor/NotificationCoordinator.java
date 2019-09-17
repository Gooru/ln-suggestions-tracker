package org.gooru.suggestions.processor.postprocessor;

interface NotificationCoordinator {

  void coordinateNotification();

  static NotificationCoordinator buildForSuggestionAdded(
      PostProcessSuggestionAddHandler.SuggestionPayload command) {
    return new SuggestionAddedNotificationCoordinator(command);
  }

}
