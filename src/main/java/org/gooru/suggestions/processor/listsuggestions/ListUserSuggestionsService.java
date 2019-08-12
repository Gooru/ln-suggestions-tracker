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

  ListSuggestionsResponse fetchSuggestionsForCourse(ListUserSuggestionsForCourseCommand command) {
    if (command.getScope() != null) {
      return fetchSuggestionsForCourseWithScope(command);
    } else {
      return fetchSuggestionsForCourseWithoutScope(command);
    }
  }

  private ListSuggestionsResponse fetchSuggestionsForCourseWithoutScope(
      ListUserSuggestionsForCourseCommand command) {
    List<SuggestionTrackerModel> suggestions = listUserSuggestionsDao
        .fetchSuggestionsForCourseWithoutScope(command.getUserId(), command.getCourseId(),
            command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao
        .countSuggestionsForCourseWithoutScope(command.getUserId(), command.getCourseId());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    return response;
  }

  private ListSuggestionsResponse fetchSuggestionsForCourseWithScope(
      ListUserSuggestionsForCourseCommand command) {
    List<SuggestionTrackerModel> suggestions = listUserSuggestionsDao
        .fetchSuggestionsForCourseWithScope(command.getUserId(), command.getCourseId(),
            command.getScope(), command.getPaginationInfo().getOffset(),
            command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao
        .countSuggestionsForCourseWithScope(command.getUserId(), command.getCourseId(),
            command.getScope());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    return response;
  }

  ListSuggestionsResponse fetchSuggestionsInClass(ListUserSuggestionsInClassCommand command) {
    if (command.getScope() != null) {
      return fetchSuggestionsInClassWithScope(command);
    } else {
      return fetchSuggestionsInClassWithoutScope(command);
    }
  }

  private ListSuggestionsResponse fetchSuggestionsInClassWithoutScope(
      ListUserSuggestionsInClassCommand command) {
    List<SuggestionTrackerModel> suggestions = listUserSuggestionsDao
        .fetchSuggestionsInClassWithoutScope(command.getUserId(), command.getClassId(),
            command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao
        .countSuggestionsInClassWithoutScope(command.getUserId(), command.getClassId());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    return response;
  }

  private ListSuggestionsResponse fetchSuggestionsInClassWithScope(
      ListUserSuggestionsInClassCommand command) {
    List<SuggestionTrackerModel> suggestions = listUserSuggestionsDao
        .fetchSuggestionsInClassWithScope(command.getUserId(), command.getClassId(),
            command.getScope(), command.getPaginationInfo().getOffset(),
            command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao
        .countSuggestionsInClassWithScope(command.getUserId(), command.getClassId(),
            command.getScope());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    return response;
  }
}
