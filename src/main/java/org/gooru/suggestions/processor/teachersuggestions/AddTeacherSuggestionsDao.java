package org.gooru.suggestions.processor.teachersuggestions;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * @author ashish on 24/11/17.
 */
interface AddTeacherSuggestionsDao {

    @SqlUpdate("insert into suggestions_tracker (ctx_user_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, "
                   + "ctx_class_id, ctx_collection_id, path_id, target_course_id, target_unit_id, target_lesson_id, "
                   + "target_collection_id, suggested_content_id, suggested_content_type, suggested_content_subtype, "
                   + "suggestion_type, accepted_by_user, accepted_at) values (:ctxUserId, :ctxCourseId, :ctxUnitId, "
                   + ":ctxLessonId, :ctxClassId, :ctxCollectionId, :pathId, :targetCourseId, :targetUnitId, "
                   + ":targetLessonId, :targetCollectionId, :suggestedContentId, :suggestedContentType, "
                   + ":suggestedContentSubType, 'teacher', null, null)")
    @GetGeneratedKeys
    public Long addTeacherSuggestion(@BindBean AddTeacherSuggestionsCommand.AddTeacherSuggestionsBean command);
}
