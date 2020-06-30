#! /bin/bash

set -e

export DATABASE=postgres
export MODE=wal
export BROKER=kafka

export SPRING_PROFILES_ACTIVE=PostgresWal,Postgres

./_build-and-test-all.sh
