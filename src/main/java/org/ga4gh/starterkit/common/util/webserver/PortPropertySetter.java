package org.ga4gh.starterkit.common.util.webserver;

import java.util.Properties;

import org.apache.commons.cli.Options;
import org.ga4gh.starterkit.common.config.ContainsServerProps;
import org.ga4gh.starterkit.common.util.CliYamlConfigLoader;
import org.ga4gh.starterkit.common.util.DeepObjectMerger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

public class PortPropertySetter {

    public static <T extends ContainsServerProps> boolean setPortProperties(Class<T> configClass, String[] args, Options options, String optionName) {
        try {
            ApplicationArguments applicationArgs = new DefaultApplicationArguments(args);
            T defaultConfig = configClass.getConstructor().newInstance();
            T userConfig = CliYamlConfigLoader.load(configClass, applicationArgs, options, optionName);
            if (userConfig != null) {
                DeepObjectMerger.merge(userConfig, defaultConfig);
            }
            T mergedConfig = defaultConfig;
            String publicApiPort = mergedConfig.getServerProps().getPublicApiPort();
            String adminApiPort = mergedConfig.getServerProps().getAdminApiPort();

            Properties systemProperties = System.getProperties();
            systemProperties.setProperty("server.port", publicApiPort);
            systemProperties.setProperty("server.admin.port", adminApiPort);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
