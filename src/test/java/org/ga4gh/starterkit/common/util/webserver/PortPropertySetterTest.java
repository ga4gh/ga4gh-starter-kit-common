package org.ga4gh.starterkit.common.util.webserver;

import java.util.Properties;

import org.apache.commons.cli.Options;
import org.ga4gh.starterkit.common.demo.DemoConfiguration;
import org.ga4gh.starterkit.common.demo.DemoYamlConfigContainer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PortPropertySetterTest {

    @DataProvider(name = "cases")
    public Object[][] getCases() {
        return new Object[][] {
            {
                new String[]{},
                "4500",
                "4501"
            },
            {
                new String[]{"--config", "./src/test/resources/config/demo-config.yml"},
                "7000",
                "7001"
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testSetPortProperties(String[] args, String expPublicApiPort, String expAdminApiPort) throws Exception {
        new PortPropertySetter();
        Options options = new DemoConfiguration().getCommandLineOptions();
        boolean success = PortPropertySetter.setPortProperties(DemoYamlConfigContainer.class, args, options, "config");
        
        Assert.assertTrue(success);
        Properties systemProperties = System.getProperties();
        Assert.assertEquals(systemProperties.getProperty("server.port"), expPublicApiPort);
        Assert.assertEquals(systemProperties.getProperty("server.admin.port"), expAdminApiPort);
    }
    
}
