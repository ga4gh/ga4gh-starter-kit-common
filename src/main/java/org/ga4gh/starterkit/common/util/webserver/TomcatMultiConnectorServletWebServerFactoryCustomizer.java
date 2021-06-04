package org.ga4gh.starterkit.common.util.webserver;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.TomcatServletWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

public class TomcatMultiConnectorServletWebServerFactoryCustomizer extends TomcatServletWebServerFactoryCustomizer {
    private final Connector[] additionalConnectors;

    public TomcatMultiConnectorServletWebServerFactoryCustomizer(ServerProperties serverProperties, Connector[] additionalConnectors) {
        super(serverProperties);
        this.additionalConnectors = additionalConnectors;
    }

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        super.customize(factory);

        if (additionalConnectors != null && additionalConnectors.length > 0) {
            factory.addAdditionalTomcatConnectors(additionalConnectors);
        }
    }
}
