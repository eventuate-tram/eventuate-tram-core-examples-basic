#! /bin/bash

set -e

if [ -n "$CIRCLECI" ]; then
    sudo apt-get install -y httpie
    pip3 install -r requirements.txt
fi

GRADLE_PROPERTIES="-P messageBroker=$BROKER -P database=${DATABASE} -P mode=${MODE}"

./gradlew $GRADLE_PROPERTIES testClasses

./gradlew $GRADLE_PROPERTIES stopServices

./gradlew $GRADLE_PROPERTIES startServices

./gradlew $GRADLE_PROPERTIES build

./run-end-to-end-test.py messages $GRADLE_PROPERTIES
./run-end-to-end-test.py commands $GRADLE_PROPERTIES
./run-end-to-end-test.py events $GRADLE_PROPERTIES

./gradlew $GRADLE_PROPERTIES stopServices
