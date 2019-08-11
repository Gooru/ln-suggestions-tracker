package org.gooru.suggestions.processor.listsuggestions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish on 24/11/17.
 */
public class SuggestionTrackerMapper implements ResultSetMapper<SuggestionTrackerModel> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionTrackerMapper.class);

  @Override
  public SuggestionTrackerModel map(int index, ResultSet r, StatementContext ctx)
      throws SQLException {
    SuggestionTrackerModel result = new SuggestionTrackerModel();

    result.setId(r.getLong(MapperFields.ID));
    result.setCtxUserId(safeStringToUUID(r.getString(MapperFields.CTX_USER_ID)));
    result.setCtxCourseId(safeStringToUUID(r.getString(MapperFields.CTX_COURSE_ID)));
    result.setCtxUnitId(safeStringToUUID(r.getString(MapperFields.CTX_UNIT_ID)));
    result.setCtxLessonId(safeStringToUUID(r.getString(MapperFields.CTX_LESSON_ID)));
    result.setCtxCollectionId(safeStringToUUID(r.getString(MapperFields.CTX_COLLECTION_ID)));
    result.setCtxClassId(safeStringToUUID(r.getString(MapperFields.CTX_CLASS_ID)));
    result.setPathId(r.getLong(MapperFields.PATH_ID));
    result.setSuggestedContentId(safeStringToUUID(r.getString(MapperFields.SUGGESTED_CONTENT_ID)));
    result.setSuggestedContentType(r.getString(MapperFields.SUGGESTED_CONTENT_TYPE));
    result.setSuggestedContentSubType(r.getString(MapperFields.SUGGESTED_CONTENT_SUBTYPE));
    result.setSuggestionType(r.getString(MapperFields.SUGGESTION_TYPE));
    result.setAcceptedByUser(r.getBoolean(MapperFields.ACCEPTED_BY_USER));
    result.setAcceptedAt(r.getDate(MapperFields.ACCEPTED_AT));
    result.setCreatedAt(r.getDate(MapperFields.CREATED_AT));
    result.setUpdatedAt(r.getDate(MapperFields.UPDATED_AT));

    return result;
  }

  private static UUID safeStringToUUID(String input) {
    if (input == null || input.isEmpty()) {
      return null;
    }
    try {
      return UUID.fromString(input);
    } catch (IllegalArgumentException e) {
      LOGGER.warn("Invalid string to be converted to UUID : '{}' ", input);
      return null;
    }
  }

  private static class MapperFields {

    private static final String ID = "id";
    private static final String CTX_USER_ID = "ctx_user_id";
    private static final String CTX_COURSE_ID = "ctx_course_id";
    private static final String CTX_UNIT_ID = "ctx_unit_id";
    private static final String CTX_LESSON_ID = "ctx_lesson_id";
    private static final String CTX_COLLECTION_ID = "ctx_collection_id";
    private static final String CTX_CLASS_ID = "ctx_class_id";
    private static final String PATH_ID = "path_id";
    private static final String SUGGESTED_CONTENT_ID = "suggested_content_id";
    private static final String SUGGESTED_CONTENT_TYPE = "suggested_content_type";
    private static final String SUGGESTED_CONTENT_SUBTYPE = "suggested_content_subtype";
    private static final String SUGGESTION_TYPE = "suggestion_type";
    private static final String ACCEPTED_BY_USER = "accepted_by_user";
    private static final String ACCEPTED_AT = "accepted_at";
    private static final String CREATED_AT = "created_at";
    private static final String UPDATED_AT = "updated_at";
  }
}
