package org.ga4gh.starterkit.common.util.webserver;

import java.util.Properties;
import java.util.Set;
import org.apache.commons.cli.Options;
import org.ga4gh.starterkit.common.config.ContainsServerProps;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.ga4gh.starterkit.common.util.CliYamlConfigLoader;
import org.ga4gh.starterkit.common.util.DeepObjectMerger;
import org.ga4gh.starterkit.common.util.logging.LoggingUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

public class ServerPropertySetter {

    private DeepObjectMerger merger;

    @Autowired
    private LoggingUtil loggingUtil;

    public ServerPropertySetter() {
        merger = new DeepObjectMerger();
    }

    public ServerPropertySetter(boolean useDefaults) {
        merger = new DeepObjectMerger(useDefaults);
    }

    public ServerPropertySetter(Set<Class<?>> mergerAtomicClasses) {
        merger = new DeepObjectMerger();
        merger.addAtomicClasses(mergerAtomicClasses);
    }

    public ServerPropertySetter(boolean useDefaults, Set<Class<?>> mergerAtomicClasses) {
        merger = new DeepObjectMerger(useDefaults);
        merger.addAtomicClasses(mergerAtomicClasses);
    }

    public <T extends ContainsServerProps> boolean setServerProperties(Class<T> configClass, String[] args, Options options, String optionName) {
        try {
            // obtain the final merged configuration object
            ApplicationArguments applicationArgs = new DefaultApplicationArguments(args);
            T defaultConfig = configClass.getConstructor().newInstance();
            T userConfig = CliYamlConfigLoader.load(configClass, applicationArgs, options, optionName);
            if (userConfig != null) {
                merger.merge(userConfig, defaultConfig);
            }
            T mergedConfig = defaultConfig;
            ServerProps serverProps = mergedConfig.getServerProps();
            String publicApiPort = serverProps.getPublicApiPort();
            String adminApiPort = serverProps.getAdminApiPort();

            // obtain system properties
            Properties systemProperties = System.getProperties();

            // set system properties for admin and public ports
            systemProperties.setProperty("server.port", publicApiPort);
            systemProperties.setProperty("server.admin.port", adminApiPort);

            // set system properties for Spring logging
            if (serverProps.getDisableSpringLogging()) {
                systemProperties.setProperty("logging.level.org.springframework", "OFF");
                systemProperties.setProperty("logging.level.root", "OFF");
                systemProperties.setProperty("spring.main.banner-mode", "off");
            }
            return true;
        } catch (Exception ex) {
            loggingUtil.error("Exception occurred: " + ex.getMessage());
            return false;
        }
    }
}
