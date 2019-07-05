#! /bin/bash

set -e

export DATABASE=postgres
export MODE=polling
export BROKER=rabbitmq

. ./set-env-postgres-polling.sh
export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE},RabbitMQ

./_build-and-test-all.sh