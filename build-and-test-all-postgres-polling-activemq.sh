#! /bin/bash

set -e

export DATABASE=postgres
export MODE=polling
export BROKER=activemq

export SPRING_PROFILES_ACTIVE=EventuatePolling,Postgres,ActiveMQ

./_build-and-test-all.sh