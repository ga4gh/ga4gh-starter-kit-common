package org.ga4gh.starterkit.common.util.webserver;

import java.util.List;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class CorsFilterBuilder {

    private ServerProps serverProps;

    public CorsFilterBuilder(ServerProps serverProps) {
        this.serverProps = serverProps;
    }

    public FilterRegistrationBean<CorsFilter> buildFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        setAllCorsProperties(source);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    private void setAllCorsProperties(UrlBasedCorsConfigurationSource source) {
        // admin, then public, order is necessary
        setSingleApiCorsProperties(
            source,
            "/admin/**",
            serverProps.getAdminApiCorsAllowedOrigins(),
            serverProps.getAdminApiCorsAllowedMethods(),
            serverProps.getAdminApiCorsAllowedHeaders()
        );

        setSingleApiCorsProperties(
            source,
            "/**",
            serverProps.getPublicApiCorsAllowedOrigins(),
            serverProps.getPublicApiCorsAllowedMethods(),
            serverProps.getPublicApiCorsAllowedHeaders()
        );
    }

    private void setSingleApiCorsProperties(UrlBasedCorsConfigurationSource source, String pattern, List<String> allowedOrigins, List<String> allowedMethods, List<String> allowedHeaders) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(allowedMethods);
        config.setAllowedHeaders(allowedHeaders);
        source.registerCorsConfiguration(pattern, config);
    }
}
