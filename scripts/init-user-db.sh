#!/usr/bin/env bash

set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER vulpes_user;
  CREATE DATABASE vulpes_db;
  GRANT ALL PRIVILEGES ON DATABASE vulpes_db TO vulpes_user;
  GRANT ALL ON SCHEMA public TO vulpes_user;
EOSQL
