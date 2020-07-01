#! /bin/bash

set -e

export DATABASE=postgres
export MODE=wal
export BROKER=activemq

export SPRING_PROFILES_ACTIVE=PostgresWal,ActiveMQ,Postgres

./_build-and-test-all.sh
