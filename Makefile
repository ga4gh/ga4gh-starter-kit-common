DEVDB := ga4gh-starter-kit.dev.db

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
