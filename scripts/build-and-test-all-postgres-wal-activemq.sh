#! /bin/bash

set -e

export DATABASE=postgres
export MODE=wal
export BROKER=activemq

./_build-and-test-all.sh
