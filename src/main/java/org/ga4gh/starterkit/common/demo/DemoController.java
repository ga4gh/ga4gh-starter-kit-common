package org.ga4gh.starterkit.common.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello, World! You have reached the PUBLIC 'hello-world' endpoint";
    }

    @GetMapping(path = "/admin/hello-world")
    public String adminHelloWorld() {
        return "Hello, World! You have reached the ADMIN 'hello-world' endpoint";
    }
}
