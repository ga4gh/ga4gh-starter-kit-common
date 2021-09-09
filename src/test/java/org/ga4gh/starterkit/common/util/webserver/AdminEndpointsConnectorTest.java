package org.ga4gh.starterkit.common.util.webserver;

import org.apache.catalina.connector.Connector;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AdminEndpointsConnectorTest {

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                "7000",
                7000
            },
            {
                "8888",
                8888
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testAdditionalConnector(String serverAdminPort, int port) {
        new AdminEndpointsConnector();
        Connector[] connectors = AdminEndpointsConnector.additionalConnector(serverAdminPort);
        Assert.assertEquals(connectors.length, 1);
        Assert.assertEquals(connectors[0].getPort(), port);
    }
}
