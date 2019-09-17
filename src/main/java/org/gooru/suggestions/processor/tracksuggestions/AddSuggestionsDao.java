package org.gooru.suggestions.processor.tracksuggestions;

import java.util.UUID;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * @author ashish
 */
interface AddSuggestionsDao {

  @GetGeneratedKeys
  @SqlUpdate(
      "insert into suggestions_tracker (user_id, course_id, unit_id, lesson_id, class_id, collection_id, ca_id, "
          + " suggested_content_id, suggestion_origin, suggestion_originator_id, suggestion_criteria, "
          + " suggested_content_type, suggested_to, accepted, accepted_at, suggestion_area, "
          + " tx_code, tx_code_type) values (:userId, :courseId, :unitId, "
          + " :lessonId, :classId, :collectionId, :caId, :suggestedContentId, :suggestionOrigin, "
          + " :suggestionOriginatorId, :suggestionCriteria, :suggestedContentType, :suggestedTo, true, "
          + " now(), :suggestionArea, :txCode, :txCodeType) ON CONFLICT DO NOTHING")
  long addSuggestion(@BindBean AddSuggestionBean command);

  @SqlQuery("select exists (select 1 from class where id = :classId and is_deleted = false and is_archived = false)")
  boolean classExists(@Bind("classId") UUID classId);

  @SqlQuery(
      "select exists (select 1 from collection where course_id = :courseId and unit_id = :unitId and "
          + " lesson_id = :lessonId and id = :collectionId and is_deleted = false)")
  boolean culcExists(@Bind("courseId") UUID courseId, @Bind("unitId") UUID unitId,
      @Bind("lessonId") UUID lessonId, @Bind("collectionId") UUID collectionId);

  @SqlQuery("select exists (select 1 from class_contents cc inner join collection c on cc.content_id = c.id "
      + " where c.is_deleted = false and cc.class_id = :classId and cc.id = :caContentId and c.id = :collectionId)")
  boolean caItemExists(@Bind("classId") UUID classId, @Bind("caContentId") Long caContentId,
      @Bind("collectionId") UUID collectionId);

  @SqlQuery("select exists (select 1 from original_resource where id = :resourceId)")
  boolean originalResourceExists(@Bind("resourceId") UUID resourceId);

  @SqlQuery("select exists (select 1 from content where id = :resourceId and content_format = 'resource'::content_format_type and is_deleted = false)")
  boolean resourceExists(@Bind("resourceId") UUID resourceId);

  @SqlQuery("select exists (select 1 from content where id = :questionId and content_format = 'question'::content_format_type and is_deleted = false)")
  boolean questionExists(@Bind("questionId") UUID questionId);

  @SqlQuery("select exists (select 1 from collection where id = :offlineActivityId and format = 'offline-activity'::content_container_type and is_deleted = false)")
  boolean offlineActivityExists(@Bind("offlineActivityId") UUID offlineActivityId);

  @SqlQuery("select exists (select 1 from collection where id = :assessmentId and format = 'assessment'::content_container_type and is_deleted = false)")
  boolean assessmentExists(@Bind("assessmentId") UUID assessmentId);

  @SqlQuery("select exists (select 1 from collection where id = :collectionId and format = 'collection'::content_container_type and is_deleted = false)")
  boolean collectionExists(@Bind("collectionId") UUID collectionId);

  @SqlQuery("select exists (select 1 from lesson where lesson_id = :lessonId and is_deleted = false)")
  boolean lessonExists(@Bind("lessonId") UUID lessonId);

  @SqlQuery("select exists (select 1 from unit where unit_id = :unitId and is_deleted = false)")
  boolean unitExists(@Bind("unitId") UUID unitId);

  @SqlQuery("select exists (select 1 from course where id = :courseId and is_deleted = false)")
  boolean courseExists(@Bind("courseId") UUID courseId);
}
