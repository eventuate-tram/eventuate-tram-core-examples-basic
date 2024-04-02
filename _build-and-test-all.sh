#! /bin/bash

set -e

GRADLE_PROPERTIES="-P messageBroker=$BROKER -P database=${DATABASE} -P mode=${MODE}"

echo $GRADLE_PROPERTIES

./gradlew $GRADLE_PROPERTIES testClasses

./gradlew $GRADLE_PROPERTIES stopServices

./gradlew $GRADLE_PROPERTIES startServices

./gradlew $GRADLE_PROPERTIES build

./gradlew $GRADLE_PROPERTIES stopServices
