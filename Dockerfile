##################################################
# BUILDER CONTAINER
##################################################

FROM eclipse-temurin:17-jdk AS builder

USER root

WORKDIR /usr/src/dependencies

# INSTALL MAKE
RUN apt update \
    && apt install build-essential -y \
    && apt install wget -y

# INSTALL SQLITE3
RUN wget https://www.sqlite.org/2021/sqlite-autoconf-3340100.tar.gz \
    && tar -zxf sqlite-autoconf-3340100.tar.gz \
    && cd sqlite-autoconf-3340100 \
    && ./configure \
    && make \
    && make install

# USER 'make' and 'sqlite3' to create the dev database
COPY Makefile Makefile
COPY database/sqlite database/sqlite
RUN make sqlite-db-refresh

##################################################
# GRADLE CONTAINER
##################################################

FROM gradle:8.3-jdk17 as gradleimage

WORKDIR /home/gradle/source

COPY build.gradle build.gradle
COPY gradlew gradlew
COPY gradle/wrapper gradle/wrapper
COPY settings.gradle settings.gradle
COPY src src

RUN chmod +x gradlew
RUN ./gradlew wrapper
RUN ./gradlew bootJar

##################################################
# FINAL CONTAINER
##################################################

FROM eclipse-temurin:17-jre

USER root

ARG VERSION

WORKDIR /usr/src/app

# copy jar, dev db, and dev resource files
COPY --from=gradleimage /home/gradle/source/build/libs/ga4gh-starter-kit-common-${VERSION}.jar ga4gh-starter-kit-common.jar
COPY --from=builder /usr/src/dependencies/ga4gh-starter-kit.dev.db ga4gh-starter-kit.dev.db
COPY src/test/resources/ src/test/resources/

ENTRYPOINT ["java", "-jar", "ga4gh-starter-kit-common.jar"]
