package org.ga4gh.starterkit.common.constant;

public class DatabasePropsDefaults {

    public static final String DRIVER_CLASS_NAME = "org.sqlite.JDBC";
    public static final String URL = "jdbc:sqlite:./ga4gh-starter-kit.dev.db";
    public static final String USERNAME = "";
    public static final String PASSWORD = "";
    public static final String POOL_SIZE = "1";
    public static final String DIALECT = "org.ga4gh.starterkit.common.hibernate.dialect.SQLiteDialect";
    public static final String HBM2DDL_AUTO = "";
    public static final String SHOW_SQL = "true";
    public static final String CURRENT_SESSION_CONTEXT_CLASS = "thread";
    public static final String DATE_CLASS = "text";
}
