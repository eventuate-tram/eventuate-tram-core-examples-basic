#! /bin/bash

set -e

export DATABASE=postgres
export MODE=wal
export BROKER=rabbitmq

./_build-and-test-all.sh
