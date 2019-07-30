package org.gooru.suggestions.processor.systemsuggestions;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish on 29/11/17.
 */
class AddSystemSuggestionsService {

  private final AddSystemSuggestionsDao addSystemSuggestionsDao;
  private AddSystemSuggestionsCommand command;
  private static final Logger LOGGER = LoggerFactory.getLogger(AddSystemSuggestionsService.class);

  AddSystemSuggestionsService(DBI dbi) {
    this.addSystemSuggestionsDao = dbi.onDemand(AddSystemSuggestionsDao.class);
  }

  void addSystemSuggestion(AddSystemSuggestionsCommand command) {
    this.command = command;
    Long suggestionId = findSuggestion();
    if (suggestionId == null) {
      addSystemSuggestion();
    } else {
      LOGGER.info("System suggestion already added, won't add. Just pretend it t be added");
    }
  }

  private Long findSuggestion() {
    if (command.getCtxClassId() == null) {
      return findSuggestionForCourse();
    } else {
      return findSuggestionForClass();
    }
  }

  private Long findSuggestionForClass() {
    if (command.getCtxCollectionId() == null) {
      return addSystemSuggestionsDao.findSuggestionForClassRootedAtLesson(command.getBean());
    } else {
      return addSystemSuggestionsDao.findSuggestionForClassRootedAtCollection(command.getBean());
    }
  }

  private Long findSuggestionForCourse() {
    if (command.getCtxCollectionId() == null) {
      return addSystemSuggestionsDao.findSuggestionForCourseRootedAtLesson(command.getBean());
    } else {
      return addSystemSuggestionsDao.findSuggestionForCourseRootedAtCollection(command.getBean());
    }
  }

  private void addSystemSuggestion() {
    addSystemSuggestionsDao.addSystemSuggestion(command.getBean());
  }

}
