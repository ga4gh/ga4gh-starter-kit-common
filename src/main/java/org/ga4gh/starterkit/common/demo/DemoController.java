package org.ga4gh.starterkit.common.demo;

import org.ga4gh.starterkit.common.exception.BadRequestException;
import org.ga4gh.starterkit.common.exception.ConflictException;
import org.ga4gh.starterkit.common.exception.CustomException;
import org.ga4gh.starterkit.common.exception.ResourceNotFoundException;
import org.ga4gh.starterkit.common.model.ServiceInfo;
import org.ga4gh.starterkit.common.util.logging.LoggingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(path = "/errors/bad-request")
    public String errorBadRequest(
        @RequestParam(name = "error", required = true) boolean error
    ) {
        if (error) {
            throw new BadRequestException("Bad request parameter(s) encountered");
        }
        return "The request did not encounter a Bad Request exception";
    }

    @GetMapping(path = "/errors/not-found")
    public String errorNotFound(
        @RequestParam(name = "error", required = true) boolean error
    ) {
        if (error) {
            throw new ResourceNotFoundException("The requested resource was not found");
        }
        return "The requested resource was found";
    }

    @GetMapping(path = "/errors/conflict")
    public String errorConflict(
        @RequestParam(name = "error", required = true) boolean error
    ) {
        if (error) {
            throw new ConflictException("Conflict with server state");
        }
        return "Not conflict with server state detected";
    }

    private void demoLogStatements() {
        loggingUtil.trace("This is a trace message");
        loggingUtil.debug("This is a debug message");
        loggingUtil.info("This is an info message");
        loggingUtil.warn("This is a warning message");
        loggingUtil.error("This is an error message");
    }
}
