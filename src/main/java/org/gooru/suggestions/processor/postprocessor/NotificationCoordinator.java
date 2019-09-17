package org.gooru.suggestions.processor.postprocessor;

/**
 * @author renuka
 */

interface NotificationCoordinator {

  void coordinateNotification();

  static NotificationCoordinator buildForTeacherSuggestionAdded(
      PostProcessTeacherSuggestionAddHandler.TeacherSuggestionPayload command) {
    return new TeacherSuggestionAddedNotificationCoordinator(command);
  }

}
