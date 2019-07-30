package org.gooru.suggestions.processor.suggestionsacceptance;

import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish on 20/11/17.
 */
class SuggestionAcceptanceService {

  private SuggestionAcceptanceCommand command;
  private final SuggestionAcceptanceDao suggestionAcceptanceDao;
  private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionAcceptanceService.class);

  SuggestionAcceptanceService(DBI dbi) {
    this.suggestionAcceptanceDao = dbi.onDemand(SuggestionAcceptanceDao.class);
  }

  void acceptSuggestion(SuggestionAcceptanceCommand command) {
    this.command = command;
    Long suggestionId = findSuggestion();
    if (suggestionId != null) {
      doAccept(suggestionId);
    } else {
      LOGGER.warn("Not able to find suggestion to accept");
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.NOT_FOUND,
          "Suggestion item not found");
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
      return suggestionAcceptanceDao.findSuggestionForClassRootedAtLesson(command.getBean());
    } else {
      return suggestionAcceptanceDao.findSuggestionForClassRootedAtCollection(command.getBean());
    }
  }

  private Long findSuggestionForCourse() {
    if (command.getCtxCollectionId() == null) {
      return suggestionAcceptanceDao.findSuggestionForCourseRootedAtLesson(command.getBean());
    } else {
      return suggestionAcceptanceDao.findSuggestionForCourseRootedAtCollection(command.getBean());
    }
  }

  private void doAccept(Long suggestionId) {
    suggestionAcceptanceDao.saveSuggestionAcceptance(suggestionId, command.getPathId());
  }

}
