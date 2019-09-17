package org.gooru.suggestions.processor.postprocessor;

import org.apache.kafka.clients.producer.Producer;

interface NotificationSenderService {

  void sendNotification(NotificationsPayload payload);

  static NotificationSenderService build(Producer<String, String> producer, String topic) {
    return new DefaultNotificationSenderService(producer, topic);
  }
}
