#! /bin/bash

set -e

export DATABASE=mysql
export MODE=binlog
export BROKER=rabbitmq

./_build-and-test-all.sh
