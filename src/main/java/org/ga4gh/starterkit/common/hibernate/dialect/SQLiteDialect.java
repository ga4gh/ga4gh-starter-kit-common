package org.ga4gh.starterkit.common.hibernate.dialect;


import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
// import org.hibernate.type.SqlTypes;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;


public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        // super();
        // registerColumnTypes();
    }

    // Shouldn't be required with new updates but keeping it commented in case we do need it
    // private void registerColumnTypes() {
    //     this.registerColumnType(SqlTypes.BIT, "integer");
    //     this.registerColumnType(SqlTypes.TINYINT, "tinyint");
    //     this.registerColumnType(SqlTypes.SMALLINT, "smallint");
    //     this.registerColumnType(SqlTypes.INTEGER, "integer");
    //     this.registerColumnType(SqlTypes.BIGINT, "bigint");
    //     this.registerColumnType(SqlTypes.FLOAT, "float");
    //     this.registerColumnType(SqlTypes.REAL, "real");
    //     this.registerColumnType(SqlTypes.DOUBLE, "double");
    //     this.registerColumnType(SqlTypes.NUMERIC, "numeric");
    //     this.registerColumnType(SqlTypes.DECIMAL, "decimal");
    //     this.registerColumnType(SqlTypes.CHAR, "char");
    //     this.registerColumnType(SqlTypes.VARCHAR, "varchar");
    //     this.registerColumnType(SqlTypes.LONGVARCHAR, "longvarchar");
    //     this.registerColumnType(SqlTypes.DATE, "date");
    //     this.registerColumnType(SqlTypes.TIME, "time");
    //     this.registerColumnType(SqlTypes.TIMESTAMP, "timestamp");
    //     this.registerColumnType(SqlTypes.BINARY, "blob");
    //     this.registerColumnType(SqlTypes.VARBINARY, "blob");
    //     this.registerColumnType(SqlTypes.LONGVARBINARY, "blob");
    //     // this.registerColumnType(SqlTypes.NULL, "null");
    //     this.registerColumnType(SqlTypes.BLOB, "blob");
    //     this.registerColumnType(SqlTypes.CLOB, "clob");
    //     this.registerColumnType(SqlTypes.BOOLEAN, "boolean");
    // }

    // @Override
    // public JdbcType resolveSqlTypeDescriptor(String columnTypeName,
    //                                         int jdbcTypeCode,
    //                                         int precision,
    //                                         int scale,
    //                                         JdbcTypeRegistry jdbcTypeRegistry) {

    //     switch (jdbcTypeCode) {
    //         case SqlTypes.NVARCHAR -> jdbcTypeCode = StandardBasicTypes.STRING.getSqlTypeCode();
    //         // ETC
    //     }

    //     return super.resolveSqlTypeDescriptor(
    //             columnTypeName,
    //             jdbcTypeCode,
    //             precision,
    //             scale,
    //             jdbcTypeRegistry
    //     );
    // }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new IdentityColumnSupportImpl();
    }

    @Override
    public boolean hasAlterTable() {
        return false;
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

    @Override
    public String getDropForeignKeyString() {
        return "";
    }

    @Override
    public String getAddForeignKeyConstraintString(String cn, String[] fk, String t, String[] pk, boolean rpk) {
        return "";
    }

    @Override
    public String getAddPrimaryKeyConstraintString(String constraintName) {
        return "";
    }
}
