package org.gooru.suggestions.processor.listsuggestions;

import java.util.List;
import org.gooru.suggestions.processor.listsuggestions.common.enricher.ContentEnricher;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish
 */
class ListUserSuggestionsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListUserSuggestionsService.class);

  private final ListUserSuggestionsDao listUserSuggestionsDao;
  private ContentEnricher contentEnricher;

  ListUserSuggestionsService(DBI dbi) {
    this.listUserSuggestionsDao = dbi.onDemand(ListUserSuggestionsDao.class);
  }

  JsonObject fetchSuggestionsForCourse(ListUserSuggestionsForCourseCommand command) {
    if (command.getScope() != null) {
      return fetchSuggestionsForCourseWithScope(command);
    } else {
      return fetchSuggestionsForCourseWithoutScope(command);
    }
  }

  private JsonObject fetchSuggestionsForCourseWithoutScope(
      ListUserSuggestionsForCourseCommand command) {
    List<SuggestionTrackerModel> suggestions = listUserSuggestionsDao
        .fetchSuggestionsForCourseWithoutScope(command.getUserId(), command.getCourseId(),
            command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionsForCourseWithoutScope(command.getUserId(),
        command.getCourseId());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    contentEnricher = ContentEnricher.buildContentEnricherForSuggestions(response);
    return contentEnricher.enrichContent();
  }

  private JsonObject fetchSuggestionsForCourseWithScope(
      ListUserSuggestionsForCourseCommand command) {
    List<SuggestionTrackerModel> suggestions =
        listUserSuggestionsDao.fetchSuggestionsForCourseWithScope(command.getUserId(),
            command.getCourseId(), command.getScope(), command.getPaginationInfo().getOffset(),
            command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionsForCourseWithScope(command.getUserId(),
        command.getCourseId(), command.getScope());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    contentEnricher = ContentEnricher.buildContentEnricherForSuggestions(response);
    return contentEnricher.enrichContent();
  }

  JsonObject fetchSuggestionsInClass(ListUserSuggestionsInClassCommand command) {
    if (command.getScope() != null) {
      return fetchSuggestionsInClassWithScope(command);
    } else {
      return fetchSuggestionsInClassWithoutScope(command);
    }
  }

  private JsonObject fetchSuggestionsInClassWithoutScope(
      ListUserSuggestionsInClassCommand command) {
    List<SuggestionTrackerModel> suggestions =
        listUserSuggestionsDao.fetchSuggestionsInClassWithoutScope(command.getUserId(),
            command.getClassId(), command.getSuggestionOrigin(),
            command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionsInClassWithoutScope(command.getUserId(),
        command.getClassId(), command.getSuggestionOrigin());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    contentEnricher = ContentEnricher.buildContentEnricherForSuggestions(response);
    return contentEnricher.enrichContent();
  }

  private JsonObject fetchSuggestionsInClassWithScope(ListUserSuggestionsInClassCommand command) {
    List<SuggestionTrackerModel> suggestions =
        listUserSuggestionsDao.fetchSuggestionsInClassWithScope(command.getUserId(),
            command.getClassId(), command.getScope(), command.getSuggestionOrigin(),
            command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionsInClassWithScope(command.getUserId(),
        command.getClassId(), command.getScope(), command.getSuggestionOrigin());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    contentEnricher = ContentEnricher.buildContentEnricherForSuggestions(response);
    return contentEnricher.enrichContent();
  }

  JsonObject fetchSuggestionsForTxCode(ListUserSuggestionsForTxCodeCommand command) {
    if (command.getClassId() != null) {
      return fetchSuggestionsForTxCodeInClass(command);
    } else {
      return fetchAllSuggestionsForTxCode(command);
    }
  }

  private JsonObject fetchAllSuggestionsForTxCode(ListUserSuggestionsForTxCodeCommand command) {
    List<SuggestionTrackerModel> suggestions = listUserSuggestionsDao.fetchSuggestionsForTxCode(
        command.getUserId(), command.getTxCode(), command.getTxCodeType(),
        command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionForTxCode(command.getUserId(),
        command.getTxCode(), command.getTxCodeType());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    contentEnricher = ContentEnricher.buildContentEnricherForTxCodeSuggestions(response);
    return contentEnricher.enrichContent();
  }

  private JsonObject fetchSuggestionsForTxCodeInClass(ListUserSuggestionsForTxCodeCommand command) {
    List<SuggestionTrackerModel> suggestions =
        listUserSuggestionsDao.fetchSuggestionsForTxCodeInClass(command.getUserId(),
            command.getTxCode(), command.getTxCodeType(), command.getClassId(),
            command.getPaginationInfo().getOffset(), command.getPaginationInfo().getMax());
    int total = listUserSuggestionsDao.countSuggestionForTxCodeInClass(command.getUserId(),
        command.getTxCode(), command.getTxCodeType(), command.getClassId());
    ListSuggestionsResponse response = new ListSuggestionsResponse();
    response.setSuggestions(suggestions);
    response.setTotal(total);
    contentEnricher = ContentEnricher.buildContentEnricherForTxCodeSuggestions(response);
    return contentEnricher.enrichContent();
  }

}
