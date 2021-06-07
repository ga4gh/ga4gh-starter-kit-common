package org.ga4gh.starterkit.common.config;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DatabasePropsTest {

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                "jdbc:sqlite:./ga4gh-starter-kit.dev.db",
                "ga4gh_user",
                "password12345",
                "1",
                "true",
                true,
                DatabaseType.sqlite
            },
            {
                "jdbc:postgresql://localhost:5432/test_db",
                "myself",
                "mypassword",
                "8",
                "false",
                true,
                DatabaseType.postgres
            },
            {
                "jdbc:mysql://localhost:5432/test_db",
                "myself",
                "mypassword",
                "1",
                "false",
                false,
                null
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testGetAllProperties(String url, String username, String password, String poolSize, String showSQL, boolean expSuccess, DatabaseType expDatabaseType) {
        DatabaseProps dbProps = new DatabaseProps();
        dbProps.setUrl(url);
        dbProps.setUsername(username);
        dbProps.setPassword(password);
        dbProps.setPoolSize(poolSize);
        dbProps.setShowSQL(showSQL);

        try {
            Properties props = dbProps.getAllProperties();
            Assert.assertEquals(props.getProperty("hibernate.connection.url"), url);
            Assert.assertEquals(props.getProperty("hibernate.connection.username"), username);
            Assert.assertEquals(props.getProperty("hibernate.connection.password"), password);
            Assert.assertEquals(props.getProperty("hibernate.connection.pool_size"), poolSize);
            Assert.assertEquals(props.getProperty("hibernate.show_sql"), showSQL);

            switch (expDatabaseType) {
                case sqlite:
                    Assert.assertEquals(props.getProperty("hibernate.connection.driver_class"), "org.sqlite.JDBC");
                    Assert.assertEquals(props.getProperty("hibernate.dialect"), "org.ga4gh.starterkit.common.hibernate.dialect.SQLiteDialect");
                    Assert.assertEquals(props.getProperty("hibernate.connection.date_class"), "text");
                    break;
                case postgres:
                    Assert.assertEquals(props.getProperty("hibernate.connection.driver_class"), "org.postgresql.Driver");
                    Assert.assertEquals(props.getProperty("hibernate.dialect"), "org.hibernate.dialect.PostgreSQLDialect");
                    break;
            }

        } catch (IllegalArgumentException ex) {
            Assert.assertFalse(expSuccess);
        }
    }
}
