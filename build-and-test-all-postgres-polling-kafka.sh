#! /bin/bash

set -e

export DATABASE=postgres
export MODE=polling
export BROKER=kafka

. ./set-env-postgres-polling.sh

./_build-and-test-all.sh