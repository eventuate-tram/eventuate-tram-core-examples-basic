#! /bin/bash

set -e

export DATABASE=postgres
export MODE=polling
export BROKER=kafka


./_build-and-test-all.sh