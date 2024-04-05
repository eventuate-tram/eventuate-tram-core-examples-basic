#! /bin/bash

set -e

GRADLE_PROPERTIES="-P messageBroker=$BROKER -P database=${DATABASE} -P mode=${MODE}"

echo $GRADLE_PROPERTIES

./gradlew $GRADLE_PROPERTIES testClasses

./gradlew $GRADLE_PROPERTIES stopServices

./gradlew $GRADLE_PROPERTIES startServices

./gradlew $GRADLE_PROPERTIES build

if [ -n "$CIRCLECI" ]; then
    pip3 install -r requirements.txt
fi

./run-end-to-end-test.py commands $GRADLE_PROPERTIES

./gradlew $GRADLE_PROPERTIES stopServices
