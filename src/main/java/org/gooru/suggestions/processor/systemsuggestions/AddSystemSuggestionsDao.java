package org.gooru.suggestions.processor.systemsuggestions;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * @author ashish
 */
interface AddSystemSuggestionsDao {

  @SqlUpdate(
      "insert into suggestions_tracker (ctx_user_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, "
          + "ctx_class_id, ctx_collection_id, path_id,   suggested_content_id, suggested_content_type, "
          + "suggested_content_subtype, suggestion_type, accepted_by_user, accepted_at) values (:ctxUserId, "
          + ":ctxCourseId, :ctxUnitId, :ctxLessonId, :ctxClassId, :ctxCollectionId, null,   "
          + ":suggestedContentId, :suggestedContentType, :suggestedContentSubType, 'system', false, null)")
  void addSystemSuggestion(@BindBean AddSystemSuggestionsCommand.AddSystemSuggestionsBean command);

  @SqlQuery(
      "select id from suggestions_tracker where ctx_user_id = :ctxUserId and ctx_course_id = :ctxCourseId and "
          + " ctx_unit_id = :ctxUnitId and ctx_lesson_id = :ctxLessonId and  ctx_collection_id = "
          + " :ctxCollectionId and suggested_content_id = :suggestedContentId and  ctx_class_id is null")
  Long findSuggestionForCourseRootedAtCollection(
      @BindBean AddSystemSuggestionsCommand.AddSystemSuggestionsBean command);

  @SqlQuery(
      "select id from suggestions_tracker where ctx_user_id = :ctxUserId and ctx_course_id = :ctxCourseId and "
          + " ctx_unit_id = :ctxUnitId and ctx_lesson_id = :ctxLessonId and  "
          + " ctx_collection_id = :ctxCollectionId and suggested_content_id = :suggestedContentId and "
          + " ctx_class_id = :ctxClassId")
  Long findSuggestionForClassRootedAtCollection(
      @BindBean AddSystemSuggestionsCommand.AddSystemSuggestionsBean command);

  @SqlQuery(
      "select id from suggestions_tracker where ctx_user_id = :ctxUserId and ctx_course_id = :ctxCourseId and "
          + " ctx_unit_id = :ctxUnitId and ctx_lesson_id = :ctxLessonId and "
          + " ctx_collection_id is null and suggested_content_id = :suggestedContentId and "
          + " ctx_class_id is null")
  Long findSuggestionForCourseRootedAtLesson(
      @BindBean AddSystemSuggestionsCommand.AddSystemSuggestionsBean command);

  @SqlQuery(
      "select id from suggestions_tracker where ctx_user_id = :ctxUserId and ctx_course_id = :ctxCourseId and "
          + " ctx_unit_id = :ctxUnitId and ctx_lesson_id = :ctxLessonId and   "
          + " ctx_collection_id is null and suggested_content_id = :suggestedContentId and "
          + " ctx_class_id = :ctxClassId")
  Long findSuggestionForClassRootedAtLesson(
      @BindBean AddSystemSuggestionsCommand.AddSystemSuggestionsBean command);

}
