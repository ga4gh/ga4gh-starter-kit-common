package org.ga4gh.starterkit.common.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.ga4gh.starterkit.common.constant.DateTimeConstants;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class ServiceInfo {

    private String id;
    private String name;
    private String description;
    private String contactUrl;
    private String documentationUrl;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeConstants.DATE_FORMAT)
    private LocalDateTime createdAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    private String environment;
    private String version;
    private ServiceType type;
    private Organization organization;

    /* Constructors */

    public ServiceInfo() {
        type = new ServiceType();
        organization = new Organization();
    }

    public ServiceInfo(String id, String name, String description, 
        String contactUrl, String documentationUrl, LocalDateTime createdAt,
        LocalDateTime updatedAt, String environment, String version, ServiceType type,
        Organization organization) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.contactUrl = contactUrl;
        this.documentationUrl = documentationUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.environment = environment;
        this.version = version;
        this.type = type;
        this.organization = organization;
    }

    /* Setters and Getters */

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public ServiceType getType() {
        return type;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    /* toString */

    @Override
    public String toString() {
        return "ServiceInfo ["
            + "id=" + id + ", "
            + "name=" + name + ", "
            + "description=" + description + ", "
            + "contactUrl=" + contactUrl + ", "
            + "documentationUrl=" + documentationUrl + ", "
            + "environment=" + environment + ", "
            + "version=" + version + "]";
    }
}
