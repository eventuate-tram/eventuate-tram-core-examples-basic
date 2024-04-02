#! /bin/bash

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

set -e

export DATABASE=postgres
export MODE=polling
export BROKER=activemq

${DIR?}/_build-and-test-all.sh
