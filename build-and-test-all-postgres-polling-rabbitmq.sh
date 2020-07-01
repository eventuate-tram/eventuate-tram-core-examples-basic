#! /bin/bash

set -e

export DATABASE=postgres
export MODE=polling
export BROKER=rabbitmq


./_build-and-test-all.sh