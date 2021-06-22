package org.ga4gh.starterkit.common.config;

import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.SCHEME;
import java.util.List;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.HOSTNAME;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.PUBLIC_API_PORT;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.ADMIN_API_PORT;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.LOG_LEVEL;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.LOG_FILE;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.DISABLE_SPRING_LOGGING;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.PUBLIC_API_CORS_ALLOWED_ORIGINS;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.PUBLIC_API_CORS_ALLOWED_METHODS;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.PUBLIC_API_CORS_ALLOWED_HEADERS;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.ADMIN_API_CORS_ALLOWED_ORIGINS;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.ADMIN_API_CORS_ALLOWED_METHODS;
import static org.ga4gh.starterkit.common.constant.ServerPropsDefaults.ADMIN_API_CORS_ALLOWED_HEADERS;

public class ServerProps {

    private String scheme;

    private String hostname;

    private String publicApiPort;

    private String adminApiPort;

    private LogLevel logLevel;

    private String logFile;

    private boolean disableSpringLogging;

    private List<String> publicApiCorsAllowedOrigins;

    private List<String> publicApiCorsAllowedMethods;

    private List<String> publicApiCorsAllowedHeaders;

    private List<String> adminApiCorsAllowedOrigins;

    private List<String> adminApiCorsAllowedMethods;

    private List<String> adminApiCorsAllowedHeaders;

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

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setDisableSpringLogging(boolean disableSpringLogging) {
        this.disableSpringLogging = disableSpringLogging;
    }

    public boolean getDisableSpringLogging() {
        return disableSpringLogging;
    }

    public void setPublicApiCorsAllowedOrigins(List<String> publicApiCorsAllowedOrigins) {
        this.publicApiCorsAllowedOrigins = publicApiCorsAllowedOrigins;
    }

    public List<String> getPublicApiCorsAllowedOrigins() {
        return publicApiCorsAllowedOrigins;
    }

    public void setPublicApiCorsAllowedMethods(List<String> publicApiCorsAllowedMethods) {
        this.publicApiCorsAllowedMethods = publicApiCorsAllowedMethods;
    }

    public List<String> getPublicApiCorsAllowedMethods() {
        return publicApiCorsAllowedMethods;
    }

    public void setPublicApiCorsAllowedHeaders(List<String> publicApiCorsAllowedHeaders) {
        this.publicApiCorsAllowedHeaders = publicApiCorsAllowedHeaders;
    }

    public List<String> getPublicApiCorsAllowedHeaders() {
        return publicApiCorsAllowedHeaders;
    }

    public void setAdminApiCorsAllowedOrigins(List<String> adminApiCorsAllowedOrigins) {
        this.adminApiCorsAllowedOrigins = adminApiCorsAllowedOrigins;
    }

    public List<String> getAdminApiCorsAllowedOrigins() {
        return adminApiCorsAllowedOrigins;
    }

    public void setAdminApiCorsAllowedMethods(List<String> adminApiCorsAllowedMethods) {
        this.adminApiCorsAllowedMethods = adminApiCorsAllowedMethods;
    }

    public List<String> getAdminApiCorsAllowedMethods() {
        return adminApiCorsAllowedMethods;
    }

    public void setAdminApiCorsAllowedHeaders(List<String> adminApiCorsAllowedHeaders) {
        this.adminApiCorsAllowedHeaders = adminApiCorsAllowedHeaders;
    }

    public List<String> getAdminApiCorsAllowedHeaders() {
        return adminApiCorsAllowedHeaders;
    }

    private void setAllDefaults() {
        setScheme(SCHEME);
        setHostname(HOSTNAME);
        setPublicApiPort(PUBLIC_API_PORT);
        setAdminApiPort(ADMIN_API_PORT);
        setLogLevel(LOG_LEVEL);
        setLogFile(LOG_FILE);
        setDisableSpringLogging(DISABLE_SPRING_LOGGING);
        setPublicApiCorsAllowedOrigins(PUBLIC_API_CORS_ALLOWED_ORIGINS);
        setPublicApiCorsAllowedMethods(PUBLIC_API_CORS_ALLOWED_METHODS);
        setPublicApiCorsAllowedHeaders(PUBLIC_API_CORS_ALLOWED_HEADERS);
        setAdminApiCorsAllowedOrigins(ADMIN_API_CORS_ALLOWED_ORIGINS);
        setAdminApiCorsAllowedMethods(ADMIN_API_CORS_ALLOWED_METHODS);
        setAdminApiCorsAllowedHeaders(ADMIN_API_CORS_ALLOWED_HEADERS);
    }
}
