#! /bin/bash -e

for messageBroker in kafka rabbitmq activemq ; do
  ./gradlew --parallel -P messageBroker=$messageBroker assemble testClasses
done
