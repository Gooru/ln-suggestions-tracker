package org.gooru.suggestions.processor.listsuggestions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.gooru.suggestions.processor.data.SuggestedContentType;
import org.gooru.suggestions.processor.data.SuggestedTo;
import org.gooru.suggestions.processor.data.SuggestionArea;
import org.gooru.suggestions.processor.data.SuggestionCriteria;
import org.gooru.suggestions.processor.data.SuggestionOrigin;
import org.gooru.suggestions.processor.data.TxCodeType;
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
    result.setUserId(safeStringToUUID(r.getString(MapperFields.USER_ID)));
    result.setCourseId(safeStringToUUID(r.getString(MapperFields.COURSE_ID)));
    result.setUnitId(safeStringToUUID(r.getString(MapperFields.UNIT_ID)));
    result.setLessonId(safeStringToUUID(r.getString(MapperFields.LESSON_ID)));
    result.setCollectionId(safeStringToUUID(r.getString(MapperFields.COLLECTION_ID)));
    result.setClassId(safeStringToUUID(r.getString(MapperFields.CLASS_ID)));
    result.setSuggestedContentId(safeStringToUUID(r.getString(MapperFields.SUGGESTED_CONTENT_ID)));
    result.setSuggestedContentType(
        SuggestedContentType.builder(r.getString(MapperFields.SUGGESTED_CONTENT_TYPE)));
    result
        .setSuggestionOrigin(SuggestionOrigin.builder(r.getString(MapperFields.SUGGESTION_ORIGIN)));
    result.setSuggestionOriginatorId(
        safeStringToUUID(r.getString(MapperFields.SUGGESTION_ORIGINATOR_ID)));
    result.setSuggestionCriteria(
        convertToSuggestionCriteria(r.getString(MapperFields.SUGGESTION_CRITERIA)));
    result.setSuggestedTo(convertToSuggestionTo(r.getString(MapperFields.SUGGESTED_TO)));
    result.setAccepted(r.getBoolean(MapperFields.ACCEPTED));
    result.setAcceptedAt(r.getDate(MapperFields.ACCEPTED_AT));
    result.setCreatedAt(r.getDate(MapperFields.CREATED_AT));
    result.setUpdatedAt(r.getDate(MapperFields.UPDATED_AT));
    result.setSuggestionArea(SuggestionArea.builder(r.getString(MapperFields.SUGGESTION_AREA)));
    result.setTxCode(r.getString(MapperFields.TX_CODE));
    result.setTxCodeType(convertToTxCodeType(r.getString(MapperFields.TX_CODE_TYPE)));
    result.setPathId(r.getLong(MapperFields.PATH_ID));
    result.setCaId(r.getLong(MapperFields.CA_ID));
    return result;
  }

  private static TxCodeType convertToTxCodeType(String codeType) {
    if (codeType != null) {
      return TxCodeType.builder(codeType);
    }
    return null;
  }

  private static SuggestedTo convertToSuggestionTo(String suggestionTo) {
    if (suggestionTo != null) {
      return SuggestedTo.builder(suggestionTo);
    }
    return null;
  }

  private static SuggestionCriteria convertToSuggestionCriteria(String criteria) {
    if (criteria != null) {
      return SuggestionCriteria.builder(criteria);
    }
    return null;
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
