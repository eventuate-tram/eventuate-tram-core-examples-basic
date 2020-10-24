#! /bin/bash

set -e

export DATABASE=postgres
export MODE=polling
export BROKER=activemq

. ./set-env-postgres-polling.sh
export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE},ActiveMQ

./_build-and-test-all.sh
