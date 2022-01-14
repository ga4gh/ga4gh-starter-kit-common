##################################################
# BUILDER CONTAINER
##################################################

FROM openjdk:11.0.12-jdk-slim-buster as builder

USER root

WORKDIR /usr/src/dependencies

# INSTALL MAKE
RUN apt update
RUN apt install build-essential -y
RUN apt install wget -y

# INSTALL SQLITE3
RUN wget https://www.sqlite.org/2021/sqlite-autoconf-3340100.tar.gz \
    && tar -zxf sqlite-autoconf-3340100.tar.gz \
    && cd sqlite-autoconf-3340100 \
    && ./configure \
    && make \
    && make install

# USER 'make' and 'sqlite3' to create the dev database
COPY Makefile Makefile
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle
COPY database/sqlite database/sqlite
RUN make sqlite-db-refresh

##################################################
# GRADLE CONTAINER
##################################################

FROM gradle:jdk11 as gradleimage

WORKDIR /home/gradle/source

COPY . .

RUN gradle build
RUN gradle wrapper
RUN ./gradlew bootJar
RUN ls -a
# COPY . /home/gradle/wrapper
#above step is experimental

##################################################
# FINAL CONTAINER
##################################################

FROM openjdk:11.0.12-jre-slim-buster

USER root

ARG VERSION

WORKDIR /usr/src/app

# copy jar, dev db, and dev resource files
# COPY --from=gradleimage build/libs/ga4gh-starter-kit-common-${VERSION}.jar ga4gh-starter-kit-common.jar
COPY --from=builder /usr/src/dependencies/ga4gh-starter-kit.dev.db ga4gh-starter-kit.dev.db
COPY src/test/resources/ src/test/resources/

ENTRYPOINT ["java", "-jar", "ga4gh-starter-kit-common.jar"]
