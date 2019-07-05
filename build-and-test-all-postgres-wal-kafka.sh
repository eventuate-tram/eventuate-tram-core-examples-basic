#! /bin/bash

set -e

export DATABASE=postgres
export MODE=wal
export BROKER=kafka

. ./set-env-postgres-wal.sh

./_build-and-test-all.sh
