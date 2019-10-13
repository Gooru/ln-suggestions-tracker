package org.gooru.suggestions.processor.listsuggestions;

import java.util.List;
import java.util.UUID;
import org.gooru.suggestions.constants.HttpConstants;
import org.gooru.suggestions.exceptions.HttpResponseWrapperException;
import org.gooru.suggestions.processor.listsuggestions.common.enricher.ContentEnricher;
import org.gooru.suggestions.processor.utilities.PGUtils;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonObject;

/**
 * @author renuka
 */
class ListUserSuggestionsInCAService {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ListUserSuggestionsInCAService.class);
  private final ListUserSuggestionsDao listUserSuggestionsDao;
  private ListUserSuggestionsInCACommand command;
  private UUID userIdFromSession;
  private ContentEnricher contentEnricher;

  ListUserSuggestionsInCAService(DBI dbi) {
    this.listUserSuggestionsDao = dbi.onDemand(ListUserSuggestionsDao.class);
  }

  JsonObject fetchSuggestionsInCA(ListUserSuggestionsInCACommand command, UUID userIdFromSession)
      throws Exception {
    this.command = command;
    this.userIdFromSession = userIdFromSession;
    validate();
    return fetchSuggestions(command);
  }

  private void validate() {
    if (command.getUserId() != null) {
      if (!(userIdFromSession.equals(command.getUserId()) && listUserSuggestionsDao
          .checkUserIsStudentForClass(command.getClassId(), command.getUserId()))) {
        LOGGER.warn("User '{}' is not student for class '{}' or class does not exists",
            command.getUserId(), command.getClassId());
        throw new HttpResponseWrapperException(HttpConstants.HttpStatus.FORBIDDEN,
            "Not authorized as Student or class does not exist");
      }
    } else {
      if (!listUserSuggestionsDao.checkUserIsAuthorizedAsTeacherForClass(command.getClassId(),
          userIdFromSession)) {
        LOGGER.warn("User '{}' is not authorized for class '{}' or class does not exists",
            command.getUserId(), command.getClassId());
        throw new HttpResponseWrapperException(HttpConstants.HttpStatus.FORBIDDEN,
            "Not authorized as teacher or coteacher or class does not exist");
      }
    }
  }

  private JsonObject fetchSuggestions(ListUserSuggestionsInCACommand command) throws Exception {
    if (command.getUserId() != null) {
      return fetchSuggestionsForUser(command);
    } else {
      return fetchAllSuggestions(command);
    }
  }

  private JsonObject fetchSuggestionsForUser(ListUserSuggestionsInCACommand command)
      throws Exception {
    if (!command.getDetail()) {
      return fetchCountOfSuggestionForStudentGivenCAIds(command);
    } else {
      return fetchSuggestionsForStudentGivenCAIds(command);
    }
  }

  private JsonObject fetchAllSuggestions(ListUserSuggestionsInCACommand command) throws Exception {
    if (!command.getDetail()) {
      return fetchCountOfAllSuggestionsMadeByTeacherGivenCAIds(command);
    } else {
      return fetchAllSuggestionsMadeByTeacherGivenCAIds(command);
    }
  }

  private JsonObject fetchSuggestionsForStudentGivenCAIds(ListUserSuggestionsInCACommand command) {
    List<SuggestionTrackerModel> suggestionsOfAllCAIds =
        listUserSuggestionsDao.fetchSuggestionsForCAIdsForGivenUser(command.getUserId(),
            command.getClassId(), PGUtils.listToPostgresArrayLong(command.getCaIds()));
    ListSuggestionsInCAResponse caResponse =
        ListUserSuggestionsInCAResponseBuilder.buildDetailedResponse(suggestionsOfAllCAIds);
    contentEnricher = ContentEnricher.buildContentEnricherForCASuggestions(caResponse);
    return contentEnricher.enrichContent();
  }

  private JsonObject fetchCountOfSuggestionForStudentGivenCAIds(
      ListUserSuggestionsInCACommand command) throws Exception {
    List<CountInfoModel> suggestions =
        listUserSuggestionsDao.countSuggestionsForCAIdsForGivenUser(command.getUserId(),
            command.getClassId(), PGUtils.listToPostgresArrayLong(command.getCaIds()));
    ListSuggestionsInCAResponse caResponse =
        ListUserSuggestionsInCAResponseBuilder.buildCountResponse(suggestions);
    return convertResponseModelToJson(caResponse);
  }

  private JsonObject fetchAllSuggestionsMadeByTeacherGivenCAIds(
      ListUserSuggestionsInCACommand command) {
    List<SuggestionTrackerModel> suggestionsOfAllCAIds =
        listUserSuggestionsDao.fetchSuggestionsForCAIds(command.getClassId(),
            PGUtils.listToPostgresArrayLong(command.getCaIds()));
    ListSuggestionsInCAResponse caResponse =
        ListUserSuggestionsInCAResponseBuilder.buildDetailedResponse(suggestionsOfAllCAIds);
    contentEnricher = ContentEnricher.buildContentEnricherForCASuggestions(caResponse);
    return contentEnricher.enrichContent();
  }

  private JsonObject fetchCountOfAllSuggestionsMadeByTeacherGivenCAIds(
      ListUserSuggestionsInCACommand command) throws Exception {
    List<CountInfoModel> suggestions = listUserSuggestionsDao.countSuggestionsForCAIds(
        command.getClassId(), PGUtils.listToPostgresArrayLong(command.getCaIds()));
    ListSuggestionsInCAResponse caResponse =
        ListUserSuggestionsInCAResponseBuilder.buildCountResponse(suggestions);
    return convertResponseModelToJson(caResponse);
  }

  private JsonObject convertResponseModelToJson(ListSuggestionsInCAResponse caResponse) {
    JsonObject response = new JsonObject();
    try {
      String resultString = new ObjectMapper().writeValueAsString(caResponse);
      response = new JsonObject(resultString);
    } catch (Exception e) {
      LOGGER.warn("Not able to convert data to JSON", e);
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.ERROR,
          "Not able to convert data to JSON");
    }
    return response;
  }

}
