#! /bin/bash

set -e

export DATABASE=postgres
export MODE=wal
export BROKER=rabbitmq

. ./set-env-postgres-wal-rabbitmq.sh

./_build-and-test-all.sh
