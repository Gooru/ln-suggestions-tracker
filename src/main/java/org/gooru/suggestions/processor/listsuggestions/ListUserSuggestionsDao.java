package org.gooru.suggestions.processor.listsuggestions;

import java.util.List;
import java.util.UUID;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * @author ashish
 */
interface ListUserSuggestionsDao {

  // Fetch In Course Suggestions
  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where course_id = :courseId and user_id = :userId and "
      + " class_id is null order by created_at desc offset :offset limit :max")
  List<SuggestionTrackerModel> fetchSuggestionsForCourseWithoutScope(@Bind("userId") UUID userId,
      @Bind("courseId") UUID courseId, @Bind("offset") int offset, @Bind("max") int max);

  @SqlQuery("select count(*) from suggestions_tracker where course_id = :courseId and user_id = :userId and class_id is null ")
  int countSuggestionsForCourseWithoutScope(@Bind("userId") UUID userId,
      @Bind("courseId") UUID courseId);

  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where user_id = :userId and course_id = :courseId "
      + " and class_id is null and suggestion_area = :scope order by created_at desc offset :offset limit :max")
  List<SuggestionTrackerModel> fetchSuggestionsForCourseWithScope(@Bind("userId") UUID userId,
      @Bind("courseId") UUID courseId, @Bind("scope") String scope, @Bind("offset") int offset,
      @Bind("max") int max);

  @SqlQuery("select count(*) from suggestions_tracker where course_id = :courseId and user_id = :userId "
      + " and class_id is null and suggestion_area = :scope")
  int countSuggestionsForCourseWithScope(@Bind("userId") UUID userId,
      @Bind("courseId") UUID courseId, @Bind("scope") String scope);

  // Fetch In Class Suggestions
  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where class_id = :classId and user_id = :userId "
      + " AND (:suggestionOrigin::text IS NULL OR suggestion_origin = :suggestionOrigin::text) "
      + " order by created_at desc offset :offset limit :max")
  List<SuggestionTrackerModel> fetchSuggestionsInClassWithoutScope(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId, @Bind("suggestionOrigin") String suggestionOrigin,
      @Bind("offset") int offset, @Bind("max") int max);

  @SqlQuery("select count(*) from suggestions_tracker where class_id = :classId and user_id = :userId "
      + " AND (:suggestionOrigin::text IS NULL OR suggestion_origin = :suggestionOrigin::text) ")
  int countSuggestionsInClassWithoutScope(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId, @Bind("suggestionOrigin") String suggestionOrigin);

  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where class_id = :classId and user_id = :userId "
      + " AND (:suggestionOrigin::text IS NULL OR suggestion_origin = :suggestionOrigin::text) "
      + " and suggestion_area = :scope order by created_at desc offset :offset limit :max")
  List<SuggestionTrackerModel> fetchSuggestionsInClassWithScope(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId, @Bind("scope") String scope,
      @Bind("suggestionOrigin") String suggestionOrigin, @Bind("offset") int offset,
      @Bind("max") int max);

  @SqlQuery("select count(*) from suggestions_tracker where user_id = :userId and class_id = :classId "
      + " AND (:suggestionOrigin::text IS NULL OR suggestion_origin = :suggestionOrigin::text) "
      + " and suggestion_area = :scope")
  int countSuggestionsInClassWithScope(@Bind("userId") UUID userId, @Bind("classId") UUID classId,
      @Bind("scope") String scope, @Bind("suggestionOrigin") String suggestionOrigin);

  // Fetch TxCode Suggestions
  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where tx_code = :txCode and tx_code_type = :txCodeType "
      + " and user_id = :userId order by created_at desc offset :offset limit :max")
  List<SuggestionTrackerModel> fetchSuggestionsForTxCode(@Bind("userId") UUID userId,
      @Bind("txCode") String txCode, @Bind("txCodeType") String txCodeType,
      @Bind("offset") int offset, @Bind("max") int max);

  @SqlQuery("select count(*) from suggestions_tracker where tx_code = :txCode and tx_code_type = :txCodeType "
      + " and user_id = :userId ")
  int countSuggestionForTxCode(@Bind("userId") UUID userId, @Bind("txCode") String txCode,
      @Bind("txCodeType") String txCodeType);

  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where tx_code = :txCode and tx_code_type = :txCodeType "
      + " and user_id = :userId and class_id = :classId order by created_at desc offset :offset limit :max")
  List<SuggestionTrackerModel> fetchSuggestionsForTxCodeInClass(@Bind("userId") UUID userId,
      @Bind("txCode") String txCode, @Bind("txCodeType") String txCodeType,
      @Bind("classId") UUID classId, @Bind("offset") int offset, @Bind("max") int max);

  @SqlQuery("select count(*) from suggestions_tracker where tx_code = :txCode and tx_code_type = :txCodeType "
      + " and user_id = :userId and class_id = :classId")
  int countSuggestionForTxCodeInClass(@Bind("userId") UUID userId, @Bind("txCode") String txCode,
      @Bind("txCodeType") String txCodeType, @Bind("classId") UUID classId);

  // Fetch CA Suggestions
  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where class_id = :classId and user_id = :userId "
      + " and ca_id = ANY(:caIds::bigint[]) order by created_at desc")
  List<SuggestionTrackerModel> fetchSuggestionsForCAIdsForGivenUser(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId, @Bind("caIds") String caIds);

  @Mapper(CountInfoMapper.class)
  @SqlQuery("select ca_id as id, count(*) as total from suggestions_tracker where class_id = :classId and user_id = :userId "
      + " and ca_id = ANY(:caIds::bigint[]) group by ca_id")
  List<CountInfoModel> countSuggestionsForCAIdsForGivenUser(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId, @Bind("caIds") String caIds);

  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where class_id = :classId and ca_id = ANY(:caIds::bigint[]) "
      + " and user_id = ANY(select user_id from class_member where class_id = :classId and is_active = true) "
      + " order by created_at desc")
  List<SuggestionTrackerModel> fetchSuggestionsForCAIds(@Bind("classId") UUID classId,
      @Bind("caIds") String caIds);

  @Mapper(CountInfoMapper.class)
  @SqlQuery("select ca_id as id, count(*) as total from suggestions_tracker where class_id = :classId and "
      + " and user_id = ANY(select user_id from class_member where class_id = :classId and is_active = true) "
      + " and ca_id = ANY(:caIds::bigint[]) group by ca_id")
  List<CountInfoModel> countSuggestionsForCAIds(@Bind("classId") UUID classId,
      @Bind("caIds") String caIds);

  @SqlQuery("select exists (select 1 from class_member where class_id = (select id from class where id = :classId "
      + "and is_deleted = false) and user_id = :userId)")
  boolean checkUserIsStudentForClass(@Bind("classId") UUID classId, @Bind("userId") UUID userId);

  @SqlQuery("select exists (select 1 from class where id = :classId and (creator_id = :userId or collaborator ?? "
      + ":userId::text ) and is_deleted = false )")
  boolean checkUserIsAuthorizedAsTeacherForClass(@Bind("classId") UUID classId,
      @Bind("userId") UUID userId);

}
