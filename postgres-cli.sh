#! /bin/bash -e

docker run $* \
   --network eventuatetramcoreexamplesbasic_default \
   --name postgresterm --link $(echo ${PWD##*/} | sed -e 's/-//g')_postgres_1:postgres --rm postgres:9.6.5 \
   sh -c 'export PGPASSWORD="eventuate"; exec psql -h postgres -U "eventuate" '
