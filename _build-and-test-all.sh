#! /bin/bash

set -e

dockerdb="./gradlew ${DATABASE}${MODE}dbCompose"
dockerall="./gradlew ${DATABASE}${MODE}Compose"

./gradlew testClasses :eventuate-tram-examples-in-memory:cleanTest :eventuate-tram-examples-in-memory:test

${dockerall}Down
${dockerall}Build
${dockerdb}Up

./wait-for-${DATABASE}.sh

${dockerall}Up

./gradlew :eventuate-tram-examples-jdbc-${BROKER}:cleanTest :eventuate-tram-examples-jdbc-${BROKER}:test

${dockerall}Down
