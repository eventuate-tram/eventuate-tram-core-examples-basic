#! /bin/bash

set -e

export DATABASE=postgres
export MODE=wal
export BROKER=rabbitmq

export SPRING_PROFILES_ACTIVE=PostgresWal,RabbitMQ,Postgres

./_build-and-test-all.sh
