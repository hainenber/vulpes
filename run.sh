#!/usr/bin/env bash

# Check if required external Docker volumes exist. Create them otherwise.
if ! docker volume ls --filter "name=opensearch-data" --format "{{.Name}}"; then
  docker volume create opensearch-data
fi
if ! docker volume ls --filter "name=postgres-data"; then
  docker volume create postgres-data
fi
