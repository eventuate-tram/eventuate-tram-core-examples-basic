#! /bin/bash

set -e

. ./set-env.sh

mvn -f eventuate-tram-examples-in-memory/pom.xml clean install

docker-compose down -v

docker-compose up -d

./wait-for-mysql.sh

mvn clean install

docker-compose down -v
