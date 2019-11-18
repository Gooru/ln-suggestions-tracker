package org.gooru.suggestions.processor.listsuggestions.common.enricher;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentMapper implements ResultSetMapper<ContentModel>  {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContentMapper.class);

  @Override
  public ContentModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    ContentModel result = new ContentModel();
    result.setId(r.getString(MapperFields.ID));
    result.setTitle(r.getString(MapperFields.TITLE));
    result.setThumbnail(r.getString(MapperFields.THUMBNAIL));
    result.setUrl(r.getString(MapperFields.URL));
    result.setTaxonomy(r.getString(MapperFields.TAXONOMY));
    return result;
  }

  private static class MapperFields {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String THUMBNAIL = "thumbnail";
    private static final String URL = "url";
    private static final String TAXONOMY = "taxonomy";

  }
}
