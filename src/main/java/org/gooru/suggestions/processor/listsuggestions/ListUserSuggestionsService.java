package org.gooru.suggestions.processor.listsuggestions;

import java.util.List;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish
 */
class ListUserSuggestionsService {

  private final ListUserSuggestionsDao listUserSuggestionsDao;

  ListUserSuggestionsService(DBI dbi) {
    this.listUserSuggestionsDao = dbi.onDemand(ListUserSuggestionsDao.class);
  }

  List<SuggestionTrackerModel> fetchSuggestionsForCourse(ListUserSuggestionsForCourseCommand command) {
    return listUserSuggestionsDao.fetchSuggestionsForCourse(command.asBean());
  }

  List<SuggestionTrackerModel> fetchSuggestionsInClass(ListUserSuggestionsInClassCommand command) {
    return listUserSuggestionsDao.fetchSuggestionsInClass(command.asBean());
  }
}
