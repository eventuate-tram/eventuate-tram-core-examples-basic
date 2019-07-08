#! /bin/bash

set -e

export DATABASE=postgres
export MODE=wal
export BROKER=activemq

. ./set-env-postgres-wal-activemq.sh

./_build-and-test-all.sh
