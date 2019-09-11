package org.gooru.suggestions.processor.listsuggestions;

import java.util.List;
import org.gooru.suggestions.processor.utilities.PGUtils;
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

  ListSuggestionsResponse fetchSuggestionsForCompetency(
      ListUserSuggestionsForCompetencyCommand command) {
    if (command.getClassId() != null) {
      return fetchSuggestionsForCompetencyInClass(command);
    } else {
      return fetchAllSuggestionsForCompetency(command);
    }
  }

  private ListSuggestionsResponse fetchAllSuggestionsForCompetency(
      ListUserSuggestionsForCompetencyCommand command) {
    List<SuggestionTrackerModel> suggestions = listUserSuggestionsDao.fetchSuggestionsForCompetency(
        command.getUserId(), command.getCodeId(), command.getPaginationInfo().getOffset(),
        command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionForCompetency(command.getUserId(),
        command.getCodeId());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    return response;
  }

  private ListSuggestionsResponse fetchSuggestionsForCompetencyInClass(
      ListUserSuggestionsForCompetencyCommand command) {
    List<SuggestionTrackerModel> suggestions =
        listUserSuggestionsDao.fetchSuggestionsForCompetencyInClass(command.getUserId(),
            command.getCodeId(), command.getClassId(), command.getPaginationInfo().getOffset(),
            command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionForCompetencyInClass(command.getUserId(),
        command.getCodeId(), command.getClassId());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    return response;
  }

  ListSuggestionsResponse fetchSuggestionsForCAId(ListUserSuggestionsInCACommand command) {
    List<SuggestionTrackerModel> suggestions =
        listUserSuggestionsDao.fetchSuggestionsForCAId(command.getUserId(), command.getClassId(),
            command.getCollectionId(), command.getCaContentId(),
            command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionsForCAId(command.getUserId(),
        command.getClassId(), command.getCollectionId(), command.getCaContentId());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    return response;
  }

  ListSuggestCountResponse fetchSuggestionsCountForCAIds(ListUserSuggestionsInCACommand command) {
    List<SuggestionCountModel> suggestions =
        listUserSuggestionsDao.countSuggestionsForCAIds(command.getUserId(), command.getClassId(),
            PGUtils.listToPostgresArrayLong(command.getCaContentIds()));
    ListSuggestCountResponse response = new ListSuggestCountResponse();
    response.setSuggestCountInfo(suggestions);
    return response;
  }
}
