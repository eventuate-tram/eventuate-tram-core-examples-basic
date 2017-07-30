#! /bin/bash

set -e

. ./set-env.sh

docker-compose down -v

docker-compose up -d

echo waiting for MySQL

sleep 15

./gradlew build

docker-compose down -v
