package org.gooru.suggestions.processor.listsuggestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

  ListSuggestionsResponse fetchSuggestionsForTxCode(ListUserSuggestionsForTxCodeCommand command) {
    if (command.getClassId() != null) {
      return fetchSuggestionsForTxCodeInClass(command);
    } else {
      return fetchAllSuggestionsForTxCode(command);
    }
  }

  private ListSuggestionsResponse fetchAllSuggestionsForTxCode(
      ListUserSuggestionsForTxCodeCommand command) {
    List<SuggestionTrackerModel> suggestions = listUserSuggestionsDao.fetchSuggestionsForTxCode(
        command.getUserId(), command.getTxCode(), command.getTxCodeType(),
        command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionForTxCode(command.getUserId(),
        command.getTxCode(), command.getTxCodeType());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    return response;
  }

  private ListSuggestionsResponse fetchSuggestionsForTxCodeInClass(
      ListUserSuggestionsForTxCodeCommand command) {
    List<SuggestionTrackerModel> suggestions =
        listUserSuggestionsDao.fetchSuggestionsForTxCodeInClass(command.getUserId(),
            command.getTxCode(), command.getTxCodeType(), command.getClassId(),
            command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionForTxCodeInClass(command.getUserId(),
        command.getTxCode(), command.getTxCodeType(), command.getClassId());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    return response;
  }

  ListSuggestionsInCAResponse fetchSuggestionsInCA(ListUserSuggestionsInCACommand command) {
    if (!command.getDetail()) {
      return fetchSuggestionsCountForCAIds(command);
    } else {
      return fetchSuggestionsForCAIds(command);
    }
  }

  private ListSuggestionsInCAResponse fetchSuggestionsForCAIds(
      ListUserSuggestionsInCACommand command) {
    List<SuggestionTrackerModel> suggestionsOfAllCAIds =
        listUserSuggestionsDao.fetchSuggestionsForCAIds(command.getUserId(), command.getClassId(),
            PGUtils.listToPostgresArrayLong(command.getCaIds()),
            command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    Map<Long, List<SuggestionTrackerModel>> groupSuggestionsByCaId = new HashMap<>();
    // Group suggestions by each ca id
    groupSuggestionsByCaId(suggestionsOfAllCAIds, groupSuggestionsByCaId);

    // Generate response object
    ListSuggestionsInCAResponse listOfSuggestionsForAllCAIds =
        generateResponseForAllCAIds(groupSuggestionsByCaId);
    return listOfSuggestionsForAllCAIds;
  }

  private ListSuggestionsInCAResponse generateResponseForAllCAIds(
      Map<Long, List<SuggestionTrackerModel>> groupSuggestionsByCaId) {
    ListSuggestionsInCAResponse listOfSuggestionsForAllCAIds = new ListSuggestionsInCAResponse();
    List<SuggestionInCAResponse> listSuggestionInCAResponse = new ArrayList<>();
    for (Entry<Long, List<SuggestionTrackerModel>> groupedSuggestion : groupSuggestionsByCaId
        .entrySet()) {
      SuggestionInCAResponse suggestionInCAResponse =
          generateSuggestionsResponseForCAId(groupedSuggestion.getKey(),
              groupedSuggestion.getValue().size(), groupedSuggestion.getValue());
      listSuggestionInCAResponse.add(suggestionInCAResponse);
    }
    listOfSuggestionsForAllCAIds.setSuggestions(listSuggestionInCAResponse);
    return listOfSuggestionsForAllCAIds;
  }

  private void groupSuggestionsByCaId(List<SuggestionTrackerModel> suggestionsOfAllCAIds,
      Map<Long, List<SuggestionTrackerModel>> groupSuggestionsByCaId) {
    if (suggestionsOfAllCAIds != null && !suggestionsOfAllCAIds.isEmpty()) {
      for (SuggestionTrackerModel suggestion : suggestionsOfAllCAIds) {
        if (groupSuggestionsByCaId.containsKey(suggestion.getCaId())) {
          List<SuggestionTrackerModel> tempSuggestions =
              (List<SuggestionTrackerModel>) groupSuggestionsByCaId.get(suggestion.getCaId());
          tempSuggestions.add(suggestion);
          groupSuggestionsByCaId.put(suggestion.getCaId(), tempSuggestions);
        } else {
          List<SuggestionTrackerModel> suggestions = new ArrayList<>();
          suggestions.add(suggestion);
          groupSuggestionsByCaId.put(suggestion.getCaId(), suggestions);
        }
      }
    }
  }

  private ListSuggestionsInCAResponse fetchSuggestionsCountForCAIds(
      ListUserSuggestionsInCACommand command) {
    List<CountInfoModel> suggestions =
        listUserSuggestionsDao.countSuggestionsForCAIds(command.getUserId(), command.getClassId(),
            PGUtils.listToPostgresArrayLong(command.getCaIds()));
    ListSuggestionsInCAResponse listOfCASuggestions = new ListSuggestionsInCAResponse();
    List<SuggestionInCAResponse> listSuggestionInCAResponse = new ArrayList<>();
    for (CountInfoModel suggestion : suggestions) {
      SuggestionInCAResponse suggestionInCAResponse =
          generateSuggestionsResponseForCAId(suggestion.getId(), suggestion.getTotal(), null);
      listSuggestionInCAResponse.add(suggestionInCAResponse);
    }
    listOfCASuggestions.setSuggestions(listSuggestionInCAResponse);
    return listOfCASuggestions;
  }

  private SuggestionInCAResponse generateSuggestionsResponseForCAId(Long caId, int total,
      List<SuggestionTrackerModel> suggestions) {
    SuggestionInCAResponse suggestionInCAResponse = new SuggestionInCAResponse();
    suggestionInCAResponse.setCaId(caId);
    suggestionInCAResponse.setTotal(total);
    suggestionInCAResponse.setSuggestions(suggestions);
    return suggestionInCAResponse;
  }
}
