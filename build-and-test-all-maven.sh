#! /bin/bash

set -e

. ./set-env-mysql-binlog.sh

./mvnw clean compile

./mvnw -am -pl eventuate-tram-examples-in-memory test

docker-compose -f docker-compose-mysql-binlog.yml down -v

docker-compose -f docker-compose-mysql-binlog.yml up -d

./wait-for-mysql.sh

./mvnw -am  -pl eventuate-tram-examples-jdbc-kafka test

docker-compose -f docker-compose-mysql-binlog.yml down -v
