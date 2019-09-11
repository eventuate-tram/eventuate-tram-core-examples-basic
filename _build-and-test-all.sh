#! /bin/bash

set -e

dockerdb="./gradlew ${DATABASE}${MODE}dbCompose"
dockerall="./gradlew ${DATABASE}${MODE}Compose"

./gradlew :eventuate-tram-examples-in-memory:cleanTest :eventuate-tram-examples-in-memory:test

${dockerall}Down
${dockerall}Build
${dockerdb}Up

./wait-for-${DATABASE}.sh

${dockerall}Up

./wait-for-services.sh $DOCKER_HOST_IP 8099
./gradlew :eventuate-tram-examples-jdbc-${BROKER}:cleanTest :eventuate-tram-examples-jdbc-${BROKER}:test

${dockerall}Down
