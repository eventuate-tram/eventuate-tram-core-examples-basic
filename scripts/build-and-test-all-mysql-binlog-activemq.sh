#! /bin/bash

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

set -e

export DATABASE=mysql
export MODE=binlog
export BROKER=activemq

${DIR?}/_build-and-test-all.sh