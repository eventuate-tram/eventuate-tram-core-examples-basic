#! /bin/bash

set -e

export SPRING_PROFILES_ACTIVE=${DATABASE?}

dockerall="./gradlew ${DATABASE}${MODE}${BROKER}Compose"

./gradlew testClasses :eventuate-tram-examples-in-memory:cleanTest :eventuate-tram-examples-in-memory:test

${dockerall}Down
${dockerall}Build

${dockerall}Up

./gradlew :eventuate-tram-examples-jdbc-${BROKER}:cleanTest :eventuate-tram-examples-jdbc-${BROKER}:test

${dockerall}Down
