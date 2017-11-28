package org.gooru.suggestions.processor.teachersuggestions;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish on 24/11/17.
 */
class AddTeacherSuggestionsService {

    private final AddTeacherSuggestionsDao addTeacherSuggestionsDao;
    private AddTeacherSuggestionsCommand addTeacherSuggestionsCommand;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddTeacherSuggestionsService.class);

    AddTeacherSuggestionsService(DBI dbi) {

        this.addTeacherSuggestionsDao = dbi.onDemand(AddTeacherSuggestionsDao.class);
    }

    void addTeacherSuggestion(AddTeacherSuggestionsCommand command) {
        addTeacherSuggestionsDao.addTeacherSuggestion(command.getBean(), command.getCtxUserIds());
    }
}
