#! /bin/bash

set -e

. ./set-env.sh

./gradlew :eventuate-tram-examples-in-memory:cleanTest :eventuate-tram-examples-in-memory:test

docker-compose down --remove-orphans -v

docker-compose up -d

./wait-for-mysql.sh

./gradlew build

docker-compose down --remove-orphans -v
