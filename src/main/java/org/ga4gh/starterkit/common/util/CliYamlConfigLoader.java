package org.ga4gh.starterkit.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.cli.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.springframework.boot.ApplicationArguments;

public class CliYamlConfigLoader {

    public static <T> T load(Class<T> loadedClass, ApplicationArguments args, Options options, String optionName) {

        T loadedObject = null;

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args.getSourceArgs());
            String configFilePath = cmd.getOptionValue(optionName);
            if (configFilePath != null) {
                File configFile = new File(configFilePath);
                if (configFile.exists() && !configFile.isDirectory()) {
                    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                    loadedObject = mapper.readValue(configFile, loadedClass);
                } else {
                    throw new FileNotFoundException();
                }
            }
        } catch (ParseException e) {
            System.out.println("ERROR: problem encountered setting config, config not set");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: problem encountered setting config, config file not found");
        } catch (IOException e) {
            System.out.println("ERROR: problem encountered setting config, config YAML could not be parsed");
        }

        return loadedObject;
    } 
    
}
