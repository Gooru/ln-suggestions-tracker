package org.gooru.suggestions.processor.listsuggestions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish
 */
public class SuggestionTrackerMapper implements ResultSetMapper<SuggestionTrackerModel> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionTrackerMapper.class);

  @Override
  public SuggestionTrackerModel map(int index, ResultSet r, StatementContext ctx)
      throws SQLException {
    SuggestionTrackerModel result = new SuggestionTrackerModel();

    result.setId(r.getLong(MapperFields.ID));
    result.setUserId(r.getString(MapperFields.USER_ID));
    result.setCourseId(r.getString(MapperFields.COURSE_ID));
    result.setUnitId(r.getString(MapperFields.UNIT_ID));
    result.setLessonId(r.getString(MapperFields.LESSON_ID));
    result.setCollectionId(r.getString(MapperFields.COLLECTION_ID));
    result.setClassId(r.getString(MapperFields.CLASS_ID));
    result.setSuggestedContentId(r.getString(MapperFields.SUGGESTED_CONTENT_ID));
    result.setSuggestedContentType(r.getString(MapperFields.SUGGESTED_CONTENT_TYPE));
    result
        .setSuggestionOrigin(r.getString(MapperFields.SUGGESTION_ORIGIN));
    result.setSuggestionOriginatorId(
        r.getString(MapperFields.SUGGESTION_ORIGINATOR_ID));
    result.setSuggestionCriteria(r.getString(MapperFields.SUGGESTION_CRITERIA));
    result.setSuggestedTo(r.getString(MapperFields.SUGGESTED_TO));
    result.setAccepted(r.getBoolean(MapperFields.ACCEPTED));
    result.setAcceptedAt(r.getDate(MapperFields.ACCEPTED_AT));
    result.setCreatedAt(r.getDate(MapperFields.CREATED_AT));
    result.setUpdatedAt(r.getDate(MapperFields.UPDATED_AT));
    result.setSuggestionArea(r.getString(MapperFields.SUGGESTION_AREA));
    result.setTxCode(r.getString(MapperFields.TX_CODE));
    result.setTxCodeType(r.getString(MapperFields.TX_CODE_TYPE));
    result.setPathId(r.getLong(MapperFields.PATH_ID));
    result.setCaId(r.getLong(MapperFields.CA_ID));
    return result;
  }

  private static class MapperFields {

    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String COURSE_ID = "course_id";
    private static final String UNIT_ID = "unit_id";
    private static final String LESSON_ID = "lesson_id";
    private static final String COLLECTION_ID = "collection_id";
    private static final String CLASS_ID = "class_id";
    private static final String SUGGESTED_CONTENT_ID = "suggested_content_id";
    private static final String SUGGESTED_CONTENT_TYPE = "suggested_content_type";
    private static final String SUGGESTION_ORIGIN = "suggestion_origin";
    private static final String SUGGESTION_ORIGINATOR_ID = "suggestion_originator_id";
    private static final String SUGGESTION_CRITERIA = "suggestion_criteria";
    private static final String SUGGESTED_TO = "suggested_to";
    private static final String ACCEPTED = "accepted";
    private static final String SUGGESTION_AREA = "suggestion_area";
    private static final String TX_CODE = "tx_code";
    private static final String TX_CODE_TYPE = "tx_code_type";
    private static final String CTX = "ctx";
    private static final String ACCEPTED_AT = "accepted_at";
    private static final String CREATED_AT = "created_at";
    private static final String UPDATED_AT = "updated_at";
    private static final String PATH_ID = "path_id";
    private static final String CA_ID = "ca_id";
  }
}
