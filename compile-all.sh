#! /bin/bash -e

for messageBroker in kafka rabbitmq activemq ; do
   echo ==================== $messageBroker ====================
  ./gradlew --parallel -P messageBroker=$messageBroker assemble testClasses
done
