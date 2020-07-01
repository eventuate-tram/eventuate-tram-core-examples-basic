#! /bin/bash

set -e

export DATABASE=mysql
export MODE=binlog
export BROKER=activemq

export SPRING_PROFILES_ACTIVE=ActiveMQ

./_build-and-test-all.sh
