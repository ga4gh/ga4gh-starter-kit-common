package org.ga4gh.starterkit.common.config;

import org.testng.Assert;
import org.testng.annotations.Test;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.SCHEME;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.HOSTNAME;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.PORT;

public class ServerPropsTest {

    @Test
    public void testServerPropsDefaults() {
        ServerProps serverProps = new ServerProps();
        Assert.assertEquals(serverProps.getScheme(), SCHEME);
        Assert.assertEquals(serverProps.getHostname(), HOSTNAME);
        Assert.assertEquals(serverProps.getPort(), PORT);
    }
}
