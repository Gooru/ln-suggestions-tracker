package org.gooru.suggestions.processor.tracksuggestions;

import org.gooru.suggestions.constants.HttpConstants.HttpStatus;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.processor.data.SuggestionArea;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish
 */
class AddSuggestionsService {

  private final AddSuggestionsDao addSuggestionsDao;
  private AddSuggestionsCommand addSuggestionsCommand;
  private static final Logger LOGGER = LoggerFactory.getLogger(AddSuggestionsService.class);
  private AddSuggestionsCommand command;

  AddSuggestionsService(DBI dbi) {

    this.addSuggestionsDao = dbi.onDemand(AddSuggestionsDao.class);
  }

  void addSuggestion(AddSuggestionsCommand command) {
    this.command = command;
    validate();
    addSuggestionsDao
        .addSuggestion(command.getBean());
  }

  private void validate() {
    if (command.getSuggestionArea() == SuggestionArea.CourseMap) {
      validateCULC();
    }
    if (command.getClassId() != null) {
      validateClassId();
    }
    validateSuggestion();
  }

  private void validateSuggestion() {
    switch (command.getSuggestedContentType()) {
      case Course:
        validateSuggestedCourseExists();
      case Unit:
        validateSuggestedUnitExists();
      case Lesson:
        validateSuggestedLessonExists();
      case Collection:
        validateSuggestedCollectionExists();
      case Assessment:
        validateSuggestedAssessmentExists();
      case OfflineActivity:
        validateOfflineActivityExists();
      case Question:
        validateQuestionExists();
      case Resource:
        validateResourceExists();
    }
  }

  private void validateResourceExists() {
    if (!addSuggestionsDao.originalResourceExists(command.getSuggestedContentId()) && !addSuggestionsDao
        .resourceExists(command.getSuggestedContentId())) {
      LOGGER.warn("Suggested question id: '{}' does not exist", command.getSuggestedContentId());
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
          "Suggested question does not exist");
    }
  }

  private void validateQuestionExists() {
    if (!addSuggestionsDao.questionExists(command.getSuggestedContentId())) {
      LOGGER.warn("Suggested question id: '{}' does not exist", command.getSuggestedContentId());
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
          "Suggested question does not exist");
    }
  }

  private void validateOfflineActivityExists() {
    if (!addSuggestionsDao.offlineActivityExists(command.getSuggestedContentId())) {
      LOGGER.warn("Suggested offline activity id: '{}' does not exist",
          command.getSuggestedContentId());
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
          "Suggested offline activity does not exist");
    }
  }

  private void validateSuggestedAssessmentExists() {
    if (!addSuggestionsDao.assessmentExists(command.getSuggestedContentId())) {
      LOGGER.warn("Suggested assessment id: '{}' does not exist", command.getSuggestedContentId());
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
          "Suggested assessment does not exist");
    }
  }

  private void validateSuggestedCollectionExists() {
    if (!addSuggestionsDao.collectionExists(command.getSuggestedContentId())) {
      LOGGER.warn("Suggested collection id: '{}' does not exist", command.getSuggestedContentId());
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
          "Suggested collection does not exist");
    }
  }

  private void validateSuggestedLessonExists() {
    if (!addSuggestionsDao.lessonExists(command.getSuggestedContentId())) {
      LOGGER.warn("Suggested lesson id: '{}' does not exist", command.getSuggestedContentId());
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
          "Suggested lesson does not exist");
    }
  }

  private void validateSuggestedUnitExists() {
    if (!addSuggestionsDao.unitExists(command.getSuggestedContentId())) {
      LOGGER.warn("Suggested unit id: '{}' does not exist", command.getSuggestedContentId());
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
          "Suggested unit does not exist");
    }
  }

  private void validateSuggestedCourseExists() {
    if (!addSuggestionsDao.courseExists(command.getSuggestedContentId())) {
      LOGGER.warn("Suggested course id: '{}' does not exist", command.getSuggestedContentId());
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
          "Suggested course does not exist");
    }
  }

  private void validateClassId() {
    if (!addSuggestionsDao.classExists(command.getClassId())) {
      LOGGER.warn("Class id: '{}' does not exist", command.getClassId());
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST, "Class does not exist");
    }
  }

  private void validateCULC() {
    if (!addSuggestionsDao
        .culcExists(command.getCourseId(), command.getUnitId(), command.getLessonId(),
            command.getCollectionId())) {
      LOGGER.warn(
          "Course: '{}', Unit: ;{}', Lesson: '{}', Collection: '{}' combination does not exist",
          command.getCourseId(), command.getUnitId(), command.getLessonId(),
          command.getCollectionId());
      throw new HttpResponseWrapperException(HttpStatus.BAD_REQUEST,
          "CULC combination does not exists");
    }
  }
}
