##################################################
# BUILDER CONTAINER
##################################################

FROM openjdk:11.0.12-jdk-slim-buster as builder

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

# FROM gradle:jdk11 as gradleimage

# WORKDIR /home/gradle/source

# COPY . .

# RUN gradle wrapper

# RUN ./gradlew bootJar

##################################################
# EXPERIMENTAL GRADLE CONTAINER
##################################################

FROM gradle:jdk11 as CACHED_GRADLE

#maybe should be changed
ENV APP_HOME=/usr/app/ 
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
#build will fail but will download dependencies (to be cached later)
RUN ./gradlew build || return 0 
COPY . .
RUN ./gradlew build

FROM gradle:jdk11 as gradleimage

ARG VERSION

ENV ARTIFACT_NAME=ga4gh-starter-kit-common-${VERSION}.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=CACHED_GRADLE $APP_HOME/build/libs/$ARTIFACT_NAME .

##################################################
# FINAL CONTAINER
##################################################

FROM openjdk:11.0.12-jre-slim-buster

USER root

# ARG VERSION

WORKDIR /usr/src/app

# copy jar, dev db, and dev resource files
COPY --from=gradleimage /home/gradle/source/build/libs/ga4gh-starter-kit-common-${VERSION}.jar ga4gh-starter-kit-common.jar
COPY --from=builder /usr/src/dependencies/ga4gh-starter-kit.dev.db ga4gh-starter-kit.dev.db
COPY src/test/resources/ src/test/resources/

ENTRYPOINT ["java", "-jar", "ga4gh-starter-kit-common.jar"]
