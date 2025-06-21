# Configuration

The GA4GH common service can be configured with custom properties, affecting behavior at runtime. This includes customization of the GA4GH common database location, contents of the service info response, etc.

This document outlines how to configure the GA4GH common service.

## Overview

Configuration is done via YAML config file, which is specified on the command line via `-c` or `--config`. For example:

```
java -jar ga4gh-starter-kit-common.jar -c /path/to/config.yml
```

If running with a custom properties file via docker, be sure to mount the config file into the container with `-v`. For example:

```
docker run -v /host/directory/with/config:/config -p 4500:4500 ga4gh/ga4gh-starter-kit-common:latest java -jar ga4gh-starter-kit-common.jar -c /config/config.yml
```

Each property has a default value. Any property that is not overridden in the YAML config will use its default.

## YAML config file

The YAML config file must follow the format of the config as described in ../src/test/resources/config/demo-config.yml. It contains a single root property of `starterKitDemo`, under which all subconfigurations are located:

```
starterKitDemo:
  serverProps:
    ...
  databaseProps:
    ...
  serviceInfo:
    ...
```

Under `starterKitDemo`, the following configuration objects can be provided:

* `serverProps`: web service general runtime props
* `databaseProps`: specify GA4GH common database location, access/auth, and other database-related properties
* `serviceInfo`: customize the output of the `/service-info` response

## serverProps

A valid `serverProps` configuration may look like the following:

```
starterKitDemo:
  serverProps:
    scheme: https
    hostname: localhost:7000
    port: 80
```

`serverProps` supports the following configurable attributes:

| Attribute | Description | Default |
|-----------|-------------|---------|
| scheme | The URL scheme/protocol by which the service can be accessed. (`http` or `https`). Allows `http` in dev/local deployments, but real-world deployments should use `https` | http |
| hostname | The URL domain name (including subdomain and port) that this service is running at. Used to reference clients back to the service | localhost:4500 |
| port | The port that the server will run on | 4500 |

## databaseProps

A valid `databaseProps` configuration may look like the following:

```
starterKitDemo:
  databaseProps:
    driverClassName: org.sqlite.JDBC
    url: jdbc:sqlite:./ga4gh-starter-kit.dev.db
    username: ga4ghuser
    password: mypa$$word123
    poolSize: 8
    dialect: org.ga4gh.starterkit.common.hibernate.dialect.SQLiteDialect
    showSQL: true
    dateClass: text
```

`databaseProps` supports the following configurable attributes:

| Attribute | Description | Default |
|-----------|-------------|---------|
| driverClassName | The driver class abstracting low-level particulars of the SQL database | org.sqlite.JDBC |
| url | Java JDBC URL to the GA4GH common database, indicating connection type and database location | jdbc:sqlite:./ga4gh-starter-kit.dev.db |
| username | Username with full access credentials to the GA4GH common database | |
| password | Password for the above user | |
| poolSize | Database connection pool size | 1 |
| dialect | Hibernate API dialect | org.ga4gh.starterkit.common.hibernate.dialect.SQLiteDialect |
| showSQL | If true, log SQL syntax for each database query | true |
| dateClass | Indicates if dates are represented as text in the database, or by another format | text |
