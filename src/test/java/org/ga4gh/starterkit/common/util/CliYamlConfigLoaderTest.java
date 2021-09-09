package org.ga4gh.starterkit.common.util;

import org.apache.commons.cli.Options;
import org.ga4gh.starterkit.common.config.DatabaseProps;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SpringBootTest
public class CliYamlConfigLoaderTest {

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                ServerProps.class,
                "./src/test/resources/config/server-props.yml",
                true,
                new String[]{"https", "myservice.ga4gh.org", "4000"}
            },
            {
                DatabaseProps.class,
                "./src/test/resources/config/database-props.yml",
                true,
                new String[]{"jdbc:sqlite:./ga4gh-starter-kit.dev.db", "ga4ghuser", "MyPassw0rd123!"}
            },
            {
                ServerProps.class,
                "./src/test/resources/config/file-not-found.yml",
                false,
                null
            },
            {
                ServerProps.class,
                "./src/test/resources/config/server-props-invalid.yml",
                false,
                null
            }
        };
    }

    private void serverPropsAssertions(ServerProps serverProps, String[] expArgs) {
        Assert.assertEquals(serverProps.getScheme(), expArgs[0]);
        Assert.assertEquals(serverProps.getHostname(), expArgs[1]);
        Assert.assertEquals(serverProps.getPublicApiPort(), expArgs[2]);
    }

    private void databasePropsAssertions(DatabaseProps databaseProps, String[] expArgs) {
        Assert.assertEquals(databaseProps.getUrl(), expArgs[0]);
        Assert.assertEquals(databaseProps.getUsername(), expArgs[1]);
        Assert.assertEquals(databaseProps.getPassword(), expArgs[2]);
    }

    @Test(dataProvider = "cases")
    private void testCliYamlLoader(Class<?> loadedClass, String configFilePath, boolean expSuccess, String[] expArgs) {
        new CliYamlConfigLoader();

        ApplicationArguments args = new DefaultApplicationArguments(new String[]{"--config", configFilePath});
        Options options = new Options();
        options.addOption("c", "config", true, "Path to config file");
        Object loaded = CliYamlConfigLoader.load(loadedClass, args, options, "config");

        if (expSuccess) {
            Assert.assertNotNull(loaded);
            switch (loaded.getClass().getSimpleName()) {
                case "ServerProps":
                    serverPropsAssertions((ServerProps) loaded, expArgs);
                    break;
                case "DatabaseProps":
                    databasePropsAssertions((DatabaseProps) loaded, expArgs);
                    break;
            }
        } else {
            Assert.assertNull(loaded);
        }
    }
}
