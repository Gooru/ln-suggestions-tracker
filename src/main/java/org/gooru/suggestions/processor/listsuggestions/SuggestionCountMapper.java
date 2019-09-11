package org.gooru.suggestions.processor.listsuggestions;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author renuka
 */
public class SuggestionCountMapper implements ResultSetMapper<SuggestionCountModel> {
  private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionCountMapper.class);

  private static final String SUGGESTION_COUNT = "suggestion_count";
  private static final String ID = "id";

  @Override
  public SuggestionCountModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    SuggestionCountModel model = new SuggestionCountModel();
    model.setCaContentId(r.getLong(ID));
    model.setTotal(r.getInt(SUGGESTION_COUNT));
    return model; 
  }

}
