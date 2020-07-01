#! /bin/bash

set -e

export DATABASE=postgres
export MODE=polling
export BROKER=rabbitmq

export SPRING_PROFILES_ACTIVE=EventuatePolling,RabbitMQ,Postgres

./_build-and-test-all.sh