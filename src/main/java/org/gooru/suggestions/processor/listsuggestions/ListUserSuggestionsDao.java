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
}
