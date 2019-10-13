package org.gooru.suggestions.processor.listsuggestions.common.enricher;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionContentCountMapper implements ResultSetMapper<CollectionContentCountModel> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContentMapper.class);

  @Override
  public CollectionContentCountModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    CollectionContentCountModel result = new CollectionContentCountModel();
    result.setId(r.getString(MapperFields.ID));
    result.setContentCount(r.getInt(MapperFields.CONTENT_COUNT));
    result.setContentformat(r.getString(MapperFields.CONTENT_FORMAT));
    return result;
  }

  private static class MapperFields {

    private static final String ID = "collection_id";
    private static final String CONTENT_COUNT = "content_count";
    private static final String CONTENT_FORMAT = "content_format";
    
  }
}
