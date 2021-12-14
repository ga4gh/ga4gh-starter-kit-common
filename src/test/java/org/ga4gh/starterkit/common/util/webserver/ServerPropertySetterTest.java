package org.ga4gh.starterkit.common.util.webserver;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.cli.Options;
import org.ga4gh.starterkit.common.demo.DemoConfiguration;
import org.ga4gh.starterkit.common.demo.DemoYamlConfigContainer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ServerPropertySetterTest {

    @DataProvider(name = "cases")
    public Object[][] getCases() {
        return new Object[][] {
            {
                new String[]{},
                "4500",
                "4501",
                false,
                false,
                true,
                null
            },
            {
                new String[]{"--config", "./src/test/resources/config/demo-config.yml"},
                "7000",
                "7001",
                true,
                false,
                true,
                null
            },
            {
                new String[]{"--config", "./src/test/resources/config/demo-config.yml"},
                "7000",
                "7001",
                true,
                true,
                true,
                null
            },
            {
                new String[]{"--config", "./src/test/resources/config/demo-config.yml"},
                "7000",
                "7001",
                true,
                true,
                true,
                new HashSet<>() {{ add(Integer.class); }}
            },
            {
                new String[]{"--config", "./src/test/resources/config/demo-config.yml"},
                "7000",
                "7001",
                true,
                false,
                true,
                new HashSet<>() {{ add(Integer.class); }}
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testSetPortProperties(String[] args, String expPublicApiPort, String expAdminApiPort,
        boolean expSpringLoggingDisabled, boolean useComplexConstructor, boolean useDefaults,
        Set<Class<?>> mergerAtomicClasses
    ) throws Exception {
        ServerPropertySetter setter = null;
        if (useComplexConstructor) {
            if (mergerAtomicClasses == null) {
                setter = new ServerPropertySetter(useDefaults);
            } else {
                setter = new ServerPropertySetter(useDefaults, mergerAtomicClasses);
            }
        } else {
            if (mergerAtomicClasses == null) {
                setter = new ServerPropertySetter();
            } else {
                setter = new ServerPropertySetter(mergerAtomicClasses);
            }
        }
        
        Options options = new DemoConfiguration().getCommandLineOptions();
        
        boolean success = setter.setServerProperties(DemoYamlConfigContainer.class, args, options, "config");
        
        Assert.assertTrue(success);
        Properties systemProperties = System.getProperties();
        Assert.assertEquals(systemProperties.getProperty("server.port"), expPublicApiPort);
        Assert.assertEquals(systemProperties.getProperty("server.admin.port"), expAdminApiPort);

        String loggingLevelOrgSpringframework = systemProperties.getProperty("logging.level.org.springframework");
        String loggingLevelRoot = systemProperties.getProperty("logging.level.root");
        String springMainBannerMode = systemProperties.getProperty("spring.main.banner-mode");
        if (expSpringLoggingDisabled) {
            Assert.assertEquals(loggingLevelOrgSpringframework, "OFF");
            Assert.assertEquals(loggingLevelRoot, "OFF");
            Assert.assertEquals(springMainBannerMode, "off");
        } else {
            Assert.assertNull(loggingLevelOrgSpringframework);
            Assert.assertNull(loggingLevelRoot);
            Assert.assertNull(springMainBannerMode);
        }
    }
}
