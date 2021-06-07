package org.ga4gh.starterkit.common.constant;

/**
 * contains default values for database properties, as well as constants
 * associated with particular database types
 */
public class DatabasePropsConstants {

    // defaults
    public static final String DEFAULT_URL = "jdbc:sqlite:./ga4gh-starter-kit.dev.db";
    public static final String DEFAULT_USERNAME = "";
    public static final String DEFAULT_PASSWORD = "";
    public static final String DEFAULT_POOL_SIZE = "1";
    public static final String DEFAULT_SHOW_SQL = "true";
    public static final String DEFAULT_CURRENT_SESSION_CONTEXT_CLASS = "thread";
    
    // constants for sqlite db type
    public static final String SQLITE_DRIVER_CLASS = "org.sqlite.JDBC";
    public static final String SQLITE_DIALECT = "org.ga4gh.starterkit.common.hibernate.dialect.SQLiteDialect";
    public static final String SQLITE_DATE_CLASS = "text";

    // constants for postgres db type
    public static final String POSTGRES_DRIVER_CLASS = "org.postgresql.Driver";
    public static final String POSTGRES_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
}
