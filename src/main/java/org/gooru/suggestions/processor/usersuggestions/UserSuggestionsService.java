package org.gooru.suggestions.processor.usersuggestions;

import java.util.List;

import org.skife.jdbi.v2.DBI;

/**
 * @author ashish on 24/11/17.
 */
class UserSuggestionsService {

    private final UserSuggestionsDao userSuggestionsDao;

    UserSuggestionsService(DBI dbi) {
        this.userSuggestionsDao = dbi.onDemand(UserSuggestionsDao.class);
    }

    List<SuggestionTrackerModel> fetchSuggestionsForCourse(UserSuggestionsForCourseCommand command) {
        return userSuggestionsDao.fetchSuggestionsForCourse(command.asBean());
    }

    List<SuggestionTrackerModel> fetchSuggestionsInClass(UserSuggestionsInClassCommand command) {
        return userSuggestionsDao.fetchSuggestionsInClass(command.asBean());
    }
}
