package org.ga4gh.starterkit.common.demo;

import org.ga4gh.starterkit.common.config.DatabaseProps;
import org.ga4gh.starterkit.common.config.ServerProps;

public class DemoYamlConfig {

    private ServerProps serverProps;
    private DatabaseProps databaseProps;

    public DemoYamlConfig() {
        serverProps = new ServerProps();
        databaseProps = new DatabaseProps();
    }

    public void setServerProps(ServerProps serverProps) {
        this.serverProps = serverProps;
    }

    public ServerProps getServerProps() {
        return serverProps;
    }

    public void setDatabaseProps(DatabaseProps databaseProps) {
        this.databaseProps = databaseProps;
    }

    public DatabaseProps getDatabaseProps() {
        return databaseProps;
    }
}
