#! /bin/bash

set -e

. ./set-env.sh

./mvnw clean compile

./mvnw -am -pl eventuate-tram-examples-in-memory test

docker-compose down -v

docker-compose up -d

./wait-for-mysql.sh

./mvnw -am  -pl eventuate-tram-examples-jdbc-kafka test

docker-compose down -v
