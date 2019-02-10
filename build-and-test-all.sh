#! /bin/bash

set -e

. ./set-env.sh

./gradlew :eventuate-tram-examples-in-memory:cleanTest :eventuate-tram-examples-in-memory:test

docker-compose down -v

docker-compose up -d

./wait-for-mysql.sh

./gradlew cleanTest build

docker-compose down -v
