package org.gooru.suggestions.processor.postprocessor;

import java.util.Objects;
import org.apache.kafka.clients.producer.Producer;
import org.gooru.suggestions.app.components.AppConfiguration;
import org.gooru.suggestions.app.components.KafkaProducerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author renuka
 */

class TeacherSuggestionAddedNotificationCoordinator implements NotificationCoordinator {


  private static final String NOTIFICATION_TEACHER_SUGGESTION = "teacher.suggestion";
  private static final String ACTION_INITIATE = "initiate";
  private final PostProcessTeacherSuggestionAddHandler.TeacherSuggestionPayload command;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(TeacherSuggestionAddedNotificationCoordinator.class);

  TeacherSuggestionAddedNotificationCoordinator(
      PostProcessTeacherSuggestionAddHandler.TeacherSuggestionPayload command) {
    this.command = command;
  }

  @Override
  public void coordinateNotification() {
    sendNotifications();
  }

  private void sendNotifications() {
    Producer<String, String> producer =
        KafkaProducerRegistry.getInstance().getTeacherSuggestionKafkaProducer();
    NotificationSenderService senderService = NotificationSenderService.build(producer,
        AppConfiguration.getInstance().getNotificationTopic());

    NotificationsPayload payload = createNotificationPayload();
    if (payload != null) {
      senderService.sendNotification(payload);
    } else {
      LOGGER.warn("Not able to create payload. Won't send notification.");
    }
  }

  private NotificationsPayload createNotificationPayload() {
    Long pathId = command.getPathId();
    String userId = Objects.toString(command.getUserId(), null);
    if (pathId != null) {
      NotificationsPayload model = new NotificationsPayload();
      model.setUserId(userId);
      model.setClassId(Objects.toString(command.getClassId(), null));
      model.setCourseId(Objects.toString(command.getCourseId(), null));
      model.setUnitId(Objects.toString(command.getUnitId(), null));
      model.setLessonId(Objects.toString(command.getLessonId(), null));
      model.setCollectionId(Objects.toString(command.getCollectionId(), null));
      model.setCaId(command.getCaId());
      model.setCurrentItemId(Objects.toString(command.getSuggestedContentId(), null));
      model.setCurrentItemType(command.getSuggestedContentType());
      model.setAction(ACTION_INITIATE);
      model.setPathId(pathId);
      model.setPathType(PathType.Teacher.getName());
      model.setNotificationType(NOTIFICATION_TEACHER_SUGGESTION);
      model.setContentSource(command.getSuggestionArea());
      model.setTxCode(command.getTxCode());
      model.setTxCodeType(command.getTxCodeType());
      return model;
    } else {
      LOGGER.warn("Path id not found for user: " + userId);
      return null;
    }
  }

}
