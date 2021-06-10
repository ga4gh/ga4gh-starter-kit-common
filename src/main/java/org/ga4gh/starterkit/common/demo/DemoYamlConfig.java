package org.ga4gh.starterkit.common.demo;

import java.time.LocalDateTime;

import org.ga4gh.starterkit.common.config.DatabaseProps;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.ga4gh.starterkit.common.constant.DateTimeConstants;
import org.ga4gh.starterkit.common.model.ServiceInfo;

public class DemoYamlConfig {

    private ServerProps serverProps;
    private DatabaseProps databaseProps;
    private ServiceInfo serviceInfo;

    public DemoYamlConfig() {
        serverProps = new ServerProps();
        databaseProps = new DatabaseProps();
        serviceInfo = new ServiceInfo();
        setDemoServiceInfoDefaults();
    }

    private void setDemoServiceInfoDefaults() {
        serviceInfo.setId("org.ga4gh.starterkit.common.demo");
        serviceInfo.setName("Starter Kit Commons Lib Demo Service");
        serviceInfo.setDescription("A generic service-info endpoint and model from the starter kit commons library");
        serviceInfo.setContactUrl("mailto:info@ga4gh.org");
        serviceInfo.setDocumentationUrl("https://ga4gh.org");
        serviceInfo.setCreatedAt(LocalDateTime.parse("2021-06-10T12:00:00Z", DateTimeConstants.DATE_FORMATTER));
        serviceInfo.setUpdatedAt(LocalDateTime.parse("2021-06-10T12:00:00Z", DateTimeConstants.DATE_FORMATTER));
        serviceInfo.setEnvironment("demo");
        serviceInfo.setVersion("1.0.0");
        serviceInfo.getOrganization().setName("Global Alliance for Genomics and Health");
        serviceInfo.getOrganization().setUrl("https://ga4gh.org");
        serviceInfo.getType().setGroup("org.ga4gh");
        serviceInfo.getType().setArtifact("demo");
        serviceInfo.getType().setVersion("1.0.0");
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

    public void setServiceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }
}
