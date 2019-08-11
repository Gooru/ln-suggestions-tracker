package org.gooru.suggestions.processor.tracksuggestions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.gooru.suggestions.processor.utilities.CollectionUtils;
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

  void addTeacherSuggestion(AddSuggestionsCommand command) {
    this.command = command;
    List<UUID> usersNotHavingSpecifiedSuggestions = findUsersNotHavingSpecifiedSuggestion();
    if (usersNotHavingSpecifiedSuggestions != null && !usersNotHavingSpecifiedSuggestions
        .isEmpty()) {
      addSuggestionsDao
          .addTeacherSuggestion(command.getBean(), usersNotHavingSpecifiedSuggestions);
    } else {
      LOGGER.info("All specified users already have specified suggestion");
    }
  }

  private List<UUID> findUsersNotHavingSpecifiedSuggestion() {
    List<UUID> usersHavingSpecifiedSuggestions = findUsersHavingSpecifiedSuggestionForClass();
    return findUsersNotInAlreadyHavingSuggestionList(usersHavingSpecifiedSuggestions);
  }

  private List<UUID> findUsersNotInAlreadyHavingSuggestionList(
      List<UUID> usersHavingSpecifiedSuggestions) {
    List<UUID> result = new ArrayList<>(command.getCtxUserIds());
    result.removeAll(usersHavingSpecifiedSuggestions);
    return result;
  }

  private List<UUID> findUsersHavingSpecifiedSuggestionForClass() {
    if (command.getCtxCollectionId() == null) {
      return addSuggestionsDao
          .findUsersHavingSpecifiedSuggestionForClassRootedAtLesson(command.getBean(),
              CollectionUtils.convertFromListUUIDToSqlArrayOfUUID(command.getCtxUserIds()));
    } else {
      return addSuggestionsDao
          .findUsersHavingSpecifiedSuggestionForClassRootedAtCollection(command.getBean(),
              CollectionUtils.convertFromListUUIDToSqlArrayOfUUID(command.getCtxUserIds()));
    }
  }
}
