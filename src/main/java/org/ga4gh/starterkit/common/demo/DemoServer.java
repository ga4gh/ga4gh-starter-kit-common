package org.ga4gh.starterkit.common.demo;

import org.apache.commons.cli.Options;
import org.ga4gh.starterkit.common.util.webserver.ServerPropertySetter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoServer {

    public static void main(String[] args) {
        boolean setupSuccess = setup(args);
        if (setupSuccess) {
            SpringApplication.run(DemoServer.class, args);
        } else {
            System.out.println("Setup failure, exiting");
        }
    }

    private static boolean setup(String[] args) {
        Options options = new DemoConfiguration().getCommandLineOptions();
        return ServerPropertySetter.setPortProperties(DemoYamlConfigContainer.class, args, options, "config");
    }
}
