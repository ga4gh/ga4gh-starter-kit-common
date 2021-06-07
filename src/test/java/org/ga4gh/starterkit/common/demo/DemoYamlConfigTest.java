package org.ga4gh.starterkit.common.demo;

import org.ga4gh.starterkit.common.config.DatabaseProps;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoYamlConfigTest {

    @Test
    public void testServerProps() {
        DemoYamlConfig config = new DemoYamlConfig();
        ServerProps serverProps = new ServerProps();
        config.setServerProps(serverProps);
        Assert.assertEquals(config.getServerProps().getScheme(), "http");
        Assert.assertEquals(config.getServerProps().getPublicApiPort(), "4500");
        Assert.assertEquals(config.getServerProps().getAdminApiPort(), "4501");
    }

    @Test
    public void testDatabaseProps() {
        DemoYamlConfig config = new DemoYamlConfig();
        DatabaseProps databaseProps = new DatabaseProps();
        config.setDatabaseProps(databaseProps);
        Assert.assertEquals(config.getDatabaseProps().getUrl(), "jdbc:sqlite:./ga4gh-starter-kit.dev.db");
        Assert.assertEquals(config.getDatabaseProps().getUsername(), "");
        Assert.assertEquals(config.getDatabaseProps().getPassword(), "");
    }
}
