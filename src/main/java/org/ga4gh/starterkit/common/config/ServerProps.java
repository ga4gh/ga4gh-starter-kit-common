package org.ga4gh.starterkit.common.config;

import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.SCHEME;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.HOSTNAME;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.PUBLIC_API_PORT;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.ADMIN_API_PORT;

public class ServerProps {

    private String scheme;

    private String hostname;

    private String publicApiPort;

    private String adminApiPort;

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

    public void setPublicApiPort(String publicApiPort) {
        this.publicApiPort = publicApiPort;
    }

    public String getPublicApiPort() {
        return publicApiPort;
    }

    public void setAdminApiPort(String adminApiPort) {
        this.adminApiPort = adminApiPort;
    }

    public String getAdminApiPort() {
        return adminApiPort;
    }

    private void setAllDefaults() {
        setScheme(SCHEME);
        setHostname(HOSTNAME);
        setPublicApiPort(PUBLIC_API_PORT);
        setAdminApiPort(ADMIN_API_PORT);
    }
}
