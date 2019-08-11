package org.gooru.suggestions.processor.utilities.jdbi;

import javax.sql.DataSource;
import org.gooru.suggestions.app.components.DataSourceRegistry;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish
 */
public final class DBICreator {

  private DBICreator() {
    throw new AssertionError();
  }

  public static DBI getDbiForDefaultDS() {
    return createDBI(DataSourceRegistry.getInstance().getDefaultDataSource());
  }

  private static DBI createDBI(DataSource dataSource) {
    DBI dbi = new DBI(dataSource);
    dbi.registerArgumentFactory(new PostgresIntegerArrayArgumentFactory());
    dbi.registerArgumentFactory(new PostgresStringArrayArgumentFactory());
    dbi.registerArgumentFactory(new PostgresUUIDArrayArgumentFactory());
    return dbi;
  }

}
