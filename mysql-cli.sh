#! /bin/bash -e

docker run $* \
   --name mysqlterm --rm \
   -e MYSQL_PORT_3306_TCP_ADDR=$DOCKER_HOST_IP -e MYSQL_PORT_3306_TCP_PORT=3306 -e MYSQL_ENV_MYSQL_ROOT_PASSWORD=rootpassword \
   mysql/mysql-server:8.0.27-1.2.6-server  \
   sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD" -o eventuate'
