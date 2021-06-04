package org.ga4gh.starterkit.common.util.webserver;

import org.apache.catalina.connector.Connector;

public class AdminEndpointsConnector {

    public static Connector[] additionalConnector(String serverAdminPort) {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(Integer.valueOf(serverAdminPort));
        return new Connector[]{connector};
    }
    
}
