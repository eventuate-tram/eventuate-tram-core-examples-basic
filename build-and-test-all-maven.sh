#! /bin/bash

set -e

property_value() {
  property=${1?}
  sed -e "/^$property=/!d" -e "s/$property=//" < gradle.properties
}

export EVENTUATE_COMMON_VERSION=$(property_value eventuateCommonImageVersion)
export EVENTUATE_MESSAGING_KAFKA_IMAGE_VERSION=$(property_value eventuateMessagingKafkaImageVersion)
export EVENTUATE_CDC_VERSION=$(property_value eventuateCdcImageVersion)


./mvnw clean compile

docker-compose -f docker-compose-mysql-binlog.yml down -v

docker-compose -f docker-compose-mysql-binlog.yml up -d

./wait-for-services.sh localhost 8099

./mvnw test

docker-compose -f docker-compose-mysql-binlog.yml down -v
