#! /bin/bash

set -e

export DATABASE=mysql
export MODE=binlog
export BROKER=rabbitmq

. ./set-env-mysql-binlog.sh
export SPRING_PROFILES_ACTIVE=RabbitMQ

./_build-and-test-all.sh
