#! /bin/bash

set -e

. ./set-env-mysql-binlog.sh

export EVENTUATE_COMMON_VERSION="0.8.0.RELEASE"
export EVENTUATE_KAFKA_VERSION="0.3.0.RELEASE"
export EVENTUATE_CDC_VERSION="0.5.0.RELEASE"

./mvnw clean compile

./mvnw -am -pl eventuate-tram-examples-in-memory test

docker-compose -f docker-compose-mysql-binlog.yml down -v

docker-compose -f docker-compose-mysql-binlog.yml up -d

./wait-for-mysql.sh

./mvnw -am  -pl eventuate-tram-examples-jdbc-kafka test

docker-compose -f docker-compose-mysql-binlog.yml down -v
