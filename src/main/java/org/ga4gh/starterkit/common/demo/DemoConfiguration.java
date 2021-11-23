package org.ga4gh.starterkit.common.demo;

import org.apache.catalina.connector.Connector;
import org.springframework.web.filter.CorsFilter;
import org.apache.commons.cli.Options;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.ga4gh.starterkit.common.model.ServiceInfo;
import org.ga4gh.starterkit.common.util.CliYamlConfigLoader;
import org.ga4gh.starterkit.common.util.DeepObjectMerger;
import org.ga4gh.starterkit.common.util.logging.LoggingUtil;
import org.ga4gh.starterkit.common.util.webserver.AdminEndpointsConnector;
import org.ga4gh.starterkit.common.util.webserver.AdminEndpointsFilter;
import org.ga4gh.starterkit.common.util.webserver.CorsFilterBuilder;
import org.ga4gh.starterkit.common.util.webserver.CustomExceptionHandling;
import org.ga4gh.starterkit.common.util.webserver.TomcatMultiConnectorServletWebServerFactoryCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@Configuration
@EnableWebMvc
public class DemoConfiguration implements WebMvcConfigurer {

    /* ******************************
     * TOMCAT SERVER
     * ****************************** */

    @Value("${server.admin.port:4501}")
    private String serverAdminPort;

    @Bean
    public WebServerFactoryCustomizer servletContainer() {
        Connector[] additionalConnectors = AdminEndpointsConnector.additionalConnector(serverAdminPort);
        ServerProperties serverProperties = new ServerProperties();
        return new TomcatMultiConnectorServletWebServerFactoryCustomizer(serverProperties, additionalConnectors);
    }
    
    @Bean
    public FilterRegistrationBean<AdminEndpointsFilter> adminEndpointsFilter() {
        return new FilterRegistrationBean<AdminEndpointsFilter>(new AdminEndpointsFilter(Integer.valueOf(serverAdminPort)));
    }

    @Bean
    public CustomExceptionHandling customExceptionHandling() {
        return new CustomExceptionHandling();
    }

    @Bean
    public DefaultHandlerExceptionResolver defaultHandlerExceptionResolver() {
        return new DefaultHandlerExceptionResolver();
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(
        @Autowired ServerProps serverProps
    ) {
        return new CorsFilterBuilder(serverProps).buildFilter();
    }

    /* ******************************
     * YAML CONFIG
     * ****************************** */

    @Bean
    public Options getCommandLineOptions() {
        final Options options = new Options();
        options.addOption("c", "config", true, "Path to DRS YAML config file");
        return options;
    }

    @Bean
    @Scope("prototype")
    @Qualifier("emptyDemoConfigContainer")
    public DemoYamlConfigContainer emptyDemoConfigContainer() {
        return new DemoYamlConfigContainer();
    }

    @Bean
    @Qualifier("defaultDemoConfigContainer")
    public DemoYamlConfigContainer defaultDemoYamlConfigContainer(
        @Qualifier("emptyDemoConfigContainer") DemoYamlConfigContainer demoConfigContainer
    ) {
        return demoConfigContainer;
    }

    @Bean
    @Qualifier("userDemoConfigContainer")
    public DemoYamlConfigContainer userDemoYamlConfigContainer(
        @Autowired ApplicationArguments args,
        @Autowired() Options options,
        @Qualifier("emptyDemoConfigContainer") DemoYamlConfigContainer demoConfigContainer
    ) {
        DemoYamlConfigContainer userConfigContainer = CliYamlConfigLoader.load(DemoYamlConfigContainer.class, args, options, "config");
        if (userConfigContainer != null) {
            return userConfigContainer;
        }
        return demoConfigContainer;
    }

    @Bean
    @Qualifier("finalDemoConfigContainer")
    public DemoYamlConfigContainer mergedDemoConfigContainer(
        @Qualifier("defaultDemoConfigContainer") DemoYamlConfigContainer defaultContainer,
        @Qualifier("userDemoConfigContainer") DemoYamlConfigContainer userContainer
    ) {
        DeepObjectMerger merger = new DeepObjectMerger();
        merger.merge(userContainer, defaultContainer);
        return defaultContainer;
    }

    @Bean
    public ServerProps serverProps(
        @Qualifier("finalDemoConfigContainer") DemoYamlConfigContainer finalContainer
    ) {
        return finalContainer.getServerProps();
    }

    @Bean
    public ServiceInfo serviceInfo(
        @Qualifier("finalDemoConfigContainer") DemoYamlConfigContainer finalContainer
    ) {
        return finalContainer.getStarterKitDemo().getServiceInfo();
    }

    /* ******************************
     * LOGGING
     * ****************************** */

    @Bean
    public LoggingUtil loggingUtil() {
        return new LoggingUtil();
    }
}
