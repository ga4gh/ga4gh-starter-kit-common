package org.ga4gh.starterkit.common.model;

public class ServiceType {

    private String group;
    private String artifact;
    private String version;

    /* Constructors */

    public ServiceType() {

    }

    public ServiceType(String group, String artifact, String version) {
        this.group = group;
        this.artifact = artifact;
        this.version = version;
    }

    /* Setters and Getters */

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
