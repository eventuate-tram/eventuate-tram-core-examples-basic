#! /bin/bash

set -e

. ./set-env.sh

mvn compile

mvn -f eventuate-tram-examples-in-memory/pom.xml install

docker-compose down -v

docker-compose up -d

./wait-for-mysql.sh

mvn install

docker-compose down -v
