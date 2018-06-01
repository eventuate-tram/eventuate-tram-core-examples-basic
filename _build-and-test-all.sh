#! /bin/bash

set -e

./gradlew :eventuate-tram-examples-in-memory:cleanTest :eventuate-tram-examples-in-memory:test

docker-compose -f docker-compose-${DATABASE}-${MODE}.yml down -v

docker-compose  -f docker-compose-${DATABASE}-${MODE}.yml up -d ${DATABASE}

./wait-for-${DATABASE}.sh

docker-compose  -f docker-compose-${DATABASE}-${MODE}.yml up -d zookeeper ${BROKER} cdcservice
./wait-for-services.sh $DOCKER_HOST_IP 8099
./gradlew :eventuate-tram-examples-jdbc-${BROKER}:cleanTest :eventuate-tram-examples-jdbc-${BROKER}:test

docker-compose -f docker-compose-${DATABASE}-${MODE}.yml down -v
