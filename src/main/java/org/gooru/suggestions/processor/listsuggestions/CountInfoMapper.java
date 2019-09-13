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
public class CountInfoMapper implements ResultSetMapper<CountInfoModel> {
  private static final Logger LOGGER = LoggerFactory.getLogger(CountInfoMapper.class);

  private static final String TOTAL = "total";
  private static final String ID = "id";

  @Override
  public CountInfoModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    CountInfoModel model = new CountInfoModel();
    model.setId(r.getLong(ID));
    model.setTotal(r.getInt(TOTAL));
    return model; 
  }

}
