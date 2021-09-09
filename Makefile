DOCKER_ORG := ga4gh
DOCKER_REPO := ga4gh-starter-kit-common
DOCKER_TAG := $(shell cat build.gradle | grep "^version" | cut -f 2 -d ' ' | sed "s/'//g")
DOCKER_IMG := ${DOCKER_ORG}/${DOCKER_REPO}:${DOCKER_TAG}
DEVDB := ga4gh-starter-kit.dev.db
JAR := build/libs/ga4gh-starter-kit-common-${TAG}.jar

Nothing:
	@echo "No target provided. Stop"

.PHONY: clean-sqlite
clean-sqlite:
	@rm -f ${DEVDB}

.PHONY: sqlite-db-build
sqlite-db-build:
	@sqlite3 ${DEVDB} < database/sqlite/create-schema.migrations.sql

.PHONY: sqlite-db-populate
sqlite-db-populate:
	@sqlite3 ${DEVDB} < database/sqlite/populate-tables.migrations.sql

.PHONY: sqlite-db-refresh
sqlite-db-refresh: clean-sqlite sqlite-db-build sqlite-db-populate

# create jar file
.PHONY: jar-build
jar-build:
	@./gradlew bootJar

# build docker image
.PHONY: docker-build
docker-build: jar-build
	docker build -t ${DOCKER_IMG} --build-arg VERSION=${DOCKER_TAG} .

# push image to docker hub
.PHONY: docker-publish
docker-publish:
	docker image push ${DOCKER_IMG}

# publish artifact to maven central repository
.PHONY: sonatype-publish
sonatype-publish:
	@./gradlew publishToSonatype
