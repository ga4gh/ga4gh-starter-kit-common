package org.ga4gh.starterkit.common.constant;

import java.util.ArrayList;
import java.util.List;
import org.ga4gh.starterkit.common.config.LogLevel;

public class ServerPropsDefaults {

    public static final String SCHEME = "http";
    public static final String HOSTNAME = "localhost";
    public static final String PUBLIC_API_PORT = "4500";
    public static final String ADMIN_API_PORT = "4501";
    public static final LogLevel LOG_LEVEL = LogLevel.DEBUG;
    public static final String LOG_FILE = null;
    public static final boolean DISABLE_SPRING_LOGGING = false;

    public static final List<String> PUBLIC_API_CORS_ALLOWED_ORIGINS = new ArrayList<>() {{
        add("*");
    }};
    public static final List<String> PUBLIC_API_CORS_ALLOWED_METHODS = new ArrayList<>() {{
        add("GET");
        add("POST");
        add("PUT");
        add("DELETE");
    }};
    public static final List<String> PUBLIC_API_CORS_ALLOWED_HEADERS = new ArrayList<>() {{
        add("*");
    }};

    public static final List<String> ADMIN_API_CORS_ALLOWED_ORIGINS = new ArrayList<>() {{
        add("*");
    }};
    public static final List<String> ADMIN_API_CORS_ALLOWED_METHODS = new ArrayList<>() {{
        add("GET");
        add("POST");
        add("PUT");
        add("DELETE");
    }};
    public static final List<String> ADMIN_API_CORS_ALLOWED_HEADERS = new ArrayList<>() {{
        add("*");
    }};
}
