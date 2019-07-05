#! /bin/bash

set -e

export DATABASE=postgres
export MODE=wal
export BROKER=rabbitmq

. ./set-env-postgres-wal.sh
export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE},RabbitMQ

./_build-and-test-all.sh
