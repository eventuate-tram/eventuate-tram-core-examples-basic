#! /bin/bash -e

docker run $*  \
   --network eventuatetramexamplesbasic_default \
   --name mysqlterm  --rm mysql:5.7.13 \
   sh -c 'exec mysql -hmysql -P3306 -uroot -prootpassword eventuate'
