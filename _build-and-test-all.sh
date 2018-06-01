#! /bin/bash

set -e

. ./set-env-${DATABASE}-${MODE}.sh

./gradlew :eventuate-tram-examples-in-memory:cleanTest :eventuate-tram-examples-in-memory:test

docker-compose -f docker-compose-${DATABASE}-${MODE}.yml down -v

docker-compose  -f docker-compose-${DATABASE}-${MODE}.yml up -d ${DATABASE}

./wait-for-${DATABASE}.sh

docker-compose  -f docker-compose-${DATABASE}-${MODE}.yml up -d
./wait-for-services.sh $DOCKER_HOST_IP 8099
./gradlew :eventuate-tram-examples-jdbc-kafka:cleanTest :eventuate-tram-examples-jdbc-kafka:test

docker-compose  -f docker-compose-${DATABASE}-${MODE}.yml stop cdcservice
docker-compose  -f docker-compose-${DATABASE}-${MODE}.yml rm --force cdcservice

if [ -z "$SPRING_PROFILES_ACTIVE" ] ; then
  export SPRING_PROFILES_ACTIVE=ActiveMQ
else
  export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE},ActiveMQ
fi



docker-compose  -f docker-compose-${DATABASE}-${MODE}.yml up -d cdcservice
./wait-for-services.sh $DOCKER_HOST_IP 8099
./gradlew :eventuate-tram-examples-jdbc-activemq:cleanTest :eventuate-tram-examples-jdbc-activemq:test

docker-compose  -f docker-compose-${DATABASE}-${MODE}.yml stop cdcservice
docker-compose  -f docker-compose-${DATABASE}-${MODE}.yml rm --force cdcservice

export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE/ActiveMQ/RabbitMQ}

docker-compose  -f docker-compose-${DATABASE}-${MODE}.yml up -d cdcservice
./wait-for-services.sh $DOCKER_HOST_IP 8099

./gradlew :eventuate-tram-examples-jdbc-rabbitmq:cleanTest :eventuate-tram-examples-jdbc-rabbitmq:test



docker-compose -f docker-compose-${DATABASE}-${MODE}.yml down -v
