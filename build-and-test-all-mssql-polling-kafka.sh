#! /bin/bash

set -e

export DATABASE=mssql
export MODE=polling
export BROKER=kafka

. ./set-env-mssql-polling.sh

./_build-and-test-all.sh