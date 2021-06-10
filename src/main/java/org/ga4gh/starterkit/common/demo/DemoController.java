package org.ga4gh.starterkit.common.demo;

import org.ga4gh.starterkit.common.model.ServiceInfo;
import org.ga4gh.starterkit.common.util.logging.LoggingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private LoggingUtil loggingUtil;

    @Autowired
    private ServiceInfo serviceInfo;

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        demoLogStatements();
        return "Hello, World! You have reached the PUBLIC 'hello-world' endpoint";
    }

    @GetMapping(path = "/admin/hello-world")
    public String adminHelloWorld() {
        demoLogStatements();
        return "Hello, World! You have reached the ADMIN 'hello-world' endpoint";
    }

    @GetMapping(path = "/service-info")
    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    private void demoLogStatements() {
        loggingUtil.trace("This is a trace message");
        loggingUtil.debug("This is a debug message");
        loggingUtil.info("This is an info message");
        loggingUtil.warn("This is a warning message");
        loggingUtil.error("This is an error message");
    }
}
