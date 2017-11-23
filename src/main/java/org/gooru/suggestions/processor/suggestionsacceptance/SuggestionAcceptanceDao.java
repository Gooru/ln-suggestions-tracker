package org.gooru.suggestions.processor.suggestionsacceptance;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * @author ashish on 20/11/17.
 */
interface SuggestionAcceptanceDao {

    @SqlUpdate("update suggestions_tracker set accepted_by_user = true, accepted_at = now(), path_id = :pathId where "
                   + "id = :id")
    void saveSuggestionAcceptance(@Bind("id") Long id, @Bind("pathId") Long pathId);

    @SqlQuery("select id from suggestions_tracker where ctx_user_id = :ctxUserId and ctx_course_id = :ctxCourseId and "
                  + " ctx_unit_id = :ctxUnitId and ctx_lesson_id = :ctxLessonId and  ctx_collection_id = "
                  + " :ctxCollectionId and suggested_content_id = :suggestedContentId and  ctx_class_id is null")
    Long findSuggestionForCourseRootedAtCollection(@BindBean
        SuggestionAcceptanceCommand.SuggestionAcceptanceBean command);

    @SqlQuery("select id from suggestions_tracker where ctx_user_id = :ctxUserId and ctx_course_id = :ctxCourseId and "
                  + " ctx_unit_id = :ctxUnitId and ctx_lesson_id = :ctxLessonId and  "
                  + " ctx_collection_id = :ctxCollectionId and suggested_content_id = :suggestedContentId and "
                  + " ctx_class_id = :ctxClassId")
    Long findSuggestionForClassRootedAtCollection(@BindBean SuggestionAcceptanceCommand.SuggestionAcceptanceBean command);

    @SqlQuery("select id from suggestions_tracker where ctx_user_id = :ctxUserId and ctx_course_id = :ctxCourseId and "
                  + " ctx_unit_id = :ctxUnitId and ctx_lesson_id = :ctxLessonId and "
                  + " ctx_collection_id is null and suggested_content_id = :suggestedContentId and "
                  + " ctx_class_id is null")
    Long findSuggestionForCourseRootedAtLesson(@BindBean SuggestionAcceptanceCommand.SuggestionAcceptanceBean command);

    @SqlQuery("select id from suggestions_tracker where ctx_user_id = :ctxUserId and ctx_course_id = :ctxCourseId and "
                  + " ctx_unit_id = :ctxUnitId and ctx_lesson_id = :ctxLessonId and   "
                  + " ctx_collection_id is null and suggested_content_id = :suggestedContentId and "
                  + " ctx_class_id = :ctxClassId")
    Long findSuggestionForClassRootedAtLesson(@BindBean SuggestionAcceptanceCommand.SuggestionAcceptanceBean command);

}
