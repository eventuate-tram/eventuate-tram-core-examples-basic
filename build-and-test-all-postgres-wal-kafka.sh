#! /bin/bash

set -e

export DATABASE=postgres
export MODE=wal
export BROKER=kafka

./_build-and-test-all.sh
