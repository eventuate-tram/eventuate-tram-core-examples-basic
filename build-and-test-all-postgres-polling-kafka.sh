#! /bin/bash

set -e

export DATABASE=postgres
export MODE=polling
export BROKER=kafka

export SPRING_PROFILES_ACTIVE=EventuatePolling,Postgres

./_build-and-test-all.sh