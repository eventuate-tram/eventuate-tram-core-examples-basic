#! /bin/bash

set -e

export DATABASE=mysql
export MODE=binlog
export BROKER=activemq

./_build-and-test-all.sh
