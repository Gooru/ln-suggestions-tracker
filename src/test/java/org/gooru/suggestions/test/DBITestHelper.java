package org.gooru.suggestions.test;

import org.gooru.suggestions.processor.utilities.jdbi.PostgresIntegerArrayArgumentFactory;
import org.gooru.suggestions.processor.utilities.jdbi.PostgresStringArrayArgumentFactory;
import org.gooru.suggestions.processor.utilities.jdbi.PostgresUUIDArrayArgumentFactory;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

/**
 * @author ashish on 12/9/17.
 */
class DBITestHelper {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/nucleus";
    private static final String DB_USER = "nucleus";

    Handle getHandle() {
        Handle handle = DBI.open(DB_URL, DB_USER, DB_USER);
        handle.registerArgumentFactory(new PostgresIntegerArrayArgumentFactory());
        handle.registerArgumentFactory(new PostgresStringArrayArgumentFactory());
        handle.registerArgumentFactory(new PostgresUUIDArrayArgumentFactory());
        return handle;
    }

    DBI getDBI() {
        DBI dbi = new DBI(DB_URL, DB_USER, DB_USER);
        dbi.registerArgumentFactory(new PostgresIntegerArrayArgumentFactory());
        dbi.registerArgumentFactory(new PostgresStringArrayArgumentFactory());
        dbi.registerArgumentFactory(new PostgresUUIDArrayArgumentFactory());
        return dbi;
    }

}
