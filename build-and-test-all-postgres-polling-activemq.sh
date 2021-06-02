#! /bin/bash

set -e

export DATABASE=postgres
export MODE=polling
export BROKER=activemq

./_build-and-test-all.sh
