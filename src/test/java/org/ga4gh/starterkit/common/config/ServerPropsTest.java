package org.ga4gh.starterkit.common.config;

import org.testng.Assert;
import org.testng.annotations.Test;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.SCHEME;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.HOSTNAME;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.PUBLIC_API_PORT;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.ADMIN_API_PORT;

public class ServerPropsTest {

    @Test
    public void testServerPropsDefaults() {
        ServerProps serverProps = new ServerProps();
        Assert.assertEquals(serverProps.getScheme(), SCHEME);
        Assert.assertEquals(serverProps.getHostname(), HOSTNAME);
        Assert.assertEquals(serverProps.getPublicApiPort(), PUBLIC_API_PORT);
        Assert.assertEquals(serverProps.getAdminApiPort(), ADMIN_API_PORT);
    }
}
