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

  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery(
      "select * from suggestions_tracker where course_id = :courseId and user_id = :userId and "
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
      @Bind("courseId") UUID courseId, @Bind("scope") String scope,
      @Bind("offset") int offset, @Bind("max") int max);

  @SqlQuery("select count(*) from suggestions_tracker where course_id = :courseId and user_id = :userId "
      + " and class_id is null and suggestion_area = :scope")
  int countSuggestionsForCourseWithScope(@Bind("userId") UUID userId,
      @Bind("courseId") UUID courseId, @Bind("scope") String scope);

  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where class_id = :classId and user_id = :userId "
      + " order by created_at desc offset :offset limit :max")
  List<SuggestionTrackerModel> fetchSuggestionsInClassWithoutScope(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId, @Bind("offset") int offset, @Bind("max") int max);

  @SqlQuery("select count(*) from suggestions_tracker where class_id = :classId and user_id = :userId ")
  int countSuggestionsInClassWithoutScope(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId);

  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where class_id = :classId and user_id = :userId "
      + " and suggestion_area = :scope order by created_at desc offset :offset limit :max")
  List<SuggestionTrackerModel> fetchSuggestionsInClassWithScope(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId, @Bind("scope") String scope,
      @Bind("offset") int offset, @Bind("max") int max);

  @SqlQuery("select count(*) from suggestions_tracker where user_id = :userId and class_id = :classId "
      + " and suggestion_area = :scope")
  int countSuggestionsInClassWithScope(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId, @Bind("scope") String scope);

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
  int countSuggestionForTxCodeInClass(@Bind("userId") UUID userId,
      @Bind("txCode") String txCode, @Bind("txCodeType") String txCodeType,
      @Bind("classId") UUID classId);

  // Fetch CA Suggestions
  @Mapper(SuggestionTrackerMapper.class)
  @SqlQuery("select * from suggestions_tracker where class_id = :classId and user_id = :userId "
      + " and ca_id = ANY(:caIds::bigint[]) order by created_at desc offset :offset limit :max")
  List<SuggestionTrackerModel> fetchSuggestionsForCAIds(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId, @Bind("caIds") String caIds, @Bind("offset") int offset,
      @Bind("max") int max);

  @Mapper(CountInfoMapper.class)
  @SqlQuery("select ca_id as id, count(*) as total from suggestions_tracker where class_id = :classId and user_id = :userId "
      + " and ca_id = ANY(:caIds::bigint[]) group by ca_id")
  List<CountInfoModel> countSuggestionsForCAIds(@Bind("userId") UUID userId,
      @Bind("classId") UUID classId, @Bind("caIds") String caIds);

}
