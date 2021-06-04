package org.ga4gh.starterkit.common.demo;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoServer {

    public static void main(String[] args) {
        Properties systemProperties = System.getProperties();
        systemProperties.setProperty("server.port", "6868");
        systemProperties.setProperty("server.admin.port", "8686");
        SpringApplication.run(DemoServer.class, args);
    }
}
