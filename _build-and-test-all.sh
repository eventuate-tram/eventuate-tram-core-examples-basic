#! /bin/bash

set -e

export SPRING_PROFILES_ACTIVE=${DATABASE?}

dockerall="./gradlew ${DATABASE}${MODE}${BROKER}Compose"

./gradlew testClasses

${dockerall}Down
${dockerall}Build

${dockerall}Up

./gradlew build -P messageBroker=$BROKER

${dockerall}Down
