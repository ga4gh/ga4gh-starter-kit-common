package org.ga4gh.starterkit.common.config;

import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.SCHEME;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.HOSTNAME;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.PORT;

public class ServerProps {

    private String scheme;

    private String hostname;

    private String port;

    public ServerProps() {
        setAllDefaults();
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getScheme() {
        return scheme;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    private void setAllDefaults() {
        this.setScheme(SCHEME);
        this.setHostname(HOSTNAME);
        this.setPort(PORT);
    }
}
