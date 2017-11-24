package org.gooru.suggestions.processor.usersuggestions;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * @author ashish on 24/11/17.
 */
interface UserSuggestionsDao {

    @Mapper(SuggestionTrackerMapper.class)
    @SqlQuery("select id, ctx_user_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, ctx_class_id, "
                  + "path_id, target_course_id, target_unit_id, target_lesson_id, target_collection_id, "
                  + "suggested_content_id, suggested_content_type, suggested_content_subtype, suggestion_type, "
                  + "accepted_by_user, accepted_at, created_at, updated_at from suggestions_tracker where ctx_user_id"
                  + " = :userId and ctx_course_id = :courseId and ctx_class_id is null order by created_at asc")
    List<SuggestionTrackerModel> fetchSuggestionsForCourse(
        @BindBean UserSuggestionsForCourseCommand.UserSuggestionsForCourseBean command);

    @Mapper(SuggestionTrackerMapper.class)
    @SqlQuery("select id, ctx_user_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, ctx_class_id, "
                  + "path_id, target_course_id, target_unit_id, target_lesson_id, target_collection_id, "
                  + "suggested_content_id, suggested_content_type, suggested_content_subtype, suggestion_type, "
                  + "accepted_by_user, accepted_at, created_at, updated_at from suggestions_tracker where ctx_user_id"
                  + " = :userId and ctx_class_id = :classId order by created_at asc")
    List<SuggestionTrackerModel> fetchSuggestionsInClass(
        @BindBean UserSuggestionsInClassCommand.UserSuggestionsInClassBean command);
}
