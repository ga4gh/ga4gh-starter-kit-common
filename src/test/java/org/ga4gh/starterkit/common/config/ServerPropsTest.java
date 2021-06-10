package org.ga4gh.starterkit.common.config;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ServerPropsTest {

    @Test
    public void testServerPropsDefaults() {
        ServerProps serverProps = new ServerProps();
        Assert.assertEquals(serverProps.getScheme(), "http");
        Assert.assertEquals(serverProps.getHostname(), "localhost");
        Assert.assertEquals(serverProps.getPublicApiPort(), "4500");
        Assert.assertEquals(serverProps.getAdminApiPort(), "4501");
        Assert.assertEquals(serverProps.getLogLevel(), LogLevel.DEBUG);
        Assert.assertEquals(serverProps.getLogFile(), null);
        Assert.assertEquals(serverProps.getDisableSpringLogging(), false);
    }
}
