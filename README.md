<img src="https://www.ga4gh.org/wp-content/themes/ga4gh-theme/gfx/GA-logo-horizontal-tag-RGB.svg" alt="GA4GH Logo" style="width: 400px;"/>

# GA4GH Starter Kit Common
Common utils library for GA4GH Starter Kit web services

## Running the GA4GH Common Service

### Native

First, clone the repository from Github:
```
git clone https://github.com/ga4gh/ga4gh-starter-kit-common.git
cd ga4gh-starter-kit-common
```
The service can be run in development mode directly via gradle:
Run with all defaults
```
./gradlew bootrun
```
Run with config file
```
./gradlew bootRun --args="--config path/to/config.yml"
```
Alternatively, the service can be built as a jar and run:
Build jar:
```
./gradlew bootJar
```
Run with all defaults
```
java -jar build/libs/ga4gh-starter-kit-common-${VERSION}.jar
```
Run with config file
```
java -jar build/libs/ga4gh-starter-kit-common-${VERSION}.jar --config path/to/config.yml
```

### Confirm server is running
You can confirm the server is running by reaching its `server-info` endpoint, you shoudl recieve a valid `ServiceInfo` response.
```
http://localhost:4500/service-info

Response:
{
    "id": "org.ga4gh.starterkit.common.demo",
    "name": "Starter Kit Commons Lib Demo Service",
    "description": "A generic service-info endpoint and model from the starter kit commons library",
    "contactUrl": "mailto:info@ga4gh.org",
    "documentationUrl": "https://ga4gh.org",
    "createdAt": "2021-06-10T12:00:00Z",
    "updatedAt": "2021-06-10T12:00:00Z",
    "environment": "demo",
    "version": "1.0.0",
    "type": {
        "group": "org.ga4gh",
        "artifact": "demo",
        "version": "1.0.0"
    },
    "organization": {
        "name": "Global Alliance for Genomics and Health",
        "url": "https://ga4gh.org"
    }
}
```

## Development
Additional setup steps to run the GA4GH common server in a local environment for development and testing.

### Setup dev database

A local SQLite database must be set up for running the GA4GH common service in a development context. If `make` and `sqlite3` are already installed on the system `PATH`, this database can be created and populated with a dev dataset by simply running: 

```
make sqlite-db-refresh
```

This will create a SQLite database named `ga4gh-starter-kit.dev.db` in the current directory.

If `make` and/or `sqlite` are not installed, [this file](./database/sqlite/create-tables.sql) contains SQLite commands for creating the database schema, and [this file](./database/sqlite/add-dev-dataset.sql) contains SQLite commands for populating it with the dev dataset.

## Configuration

Please see the [Configuration page](./CONFIGURATION.md) for instructions on how to configure the GA4GH common service with custom properties.

## Changelog

### v0.5.7
* patched log4j dependencies to v2.16.0 to avoid [Log4j Vulnerability](https://www.cisa.gov/uscert/apache-log4j-vulnerability-guidance)
