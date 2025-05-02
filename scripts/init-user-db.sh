#!/usr/bin/env bash

set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER flyway_user WITH CREATEROLE;
  GRANT ALL PRIVILEGES ON DATABASE vulpes_db TO flyway_user;
  GRANT ALL ON SCHEMA public TO flyway_user;
EOSQL
