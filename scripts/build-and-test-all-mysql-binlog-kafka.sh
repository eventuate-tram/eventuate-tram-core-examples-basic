#! /bin/bash

set -e

export DATABASE=mysql
export MODE=binlog
export BROKER=kafka

./_build-and-test-all.sh
