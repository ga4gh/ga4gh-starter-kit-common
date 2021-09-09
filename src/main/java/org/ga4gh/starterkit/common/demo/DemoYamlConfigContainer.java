package org.ga4gh.starterkit.common.demo;

import org.ga4gh.starterkit.common.config.ContainsServerProps;
import org.ga4gh.starterkit.common.config.ServerProps;

public class DemoYamlConfigContainer implements ContainsServerProps {

    private DemoYamlConfig starterKitDemo;

    public DemoYamlConfigContainer() {
        starterKitDemo = new DemoYamlConfig();
    }

    public ServerProps getServerProps() {
        return getStarterKitDemo().getServerProps();
    }

    public void setStarterKitDemo(DemoYamlConfig starterKitDemo) {
        this.starterKitDemo = starterKitDemo;
    }

    public DemoYamlConfig getStarterKitDemo() {
        return starterKitDemo;
    }
}