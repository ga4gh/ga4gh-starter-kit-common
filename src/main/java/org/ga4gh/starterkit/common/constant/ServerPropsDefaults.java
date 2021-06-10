package org.ga4gh.starterkit.common.constant;

import org.ga4gh.starterkit.common.config.LogLevel;

public class ServerPropsDefaults {

    public static final String SCHEME = "http";
    public static final String HOSTNAME = "localhost";
    public static final String PUBLIC_API_PORT = "4500";
    public static final String ADMIN_API_PORT = "4501";
    public static final LogLevel LOG_LEVEL = LogLevel.DEBUG;
    public static final String LOG_FILE = null;
    public static final boolean DISABLE_SPRING_LOGGING = false;
}
