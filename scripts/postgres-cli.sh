#! /bin/bash -e

docker run ${1:--it}  \
   --name postgresterm  --network=docker-compose-files_default --rm \
   --rm postgres:12.16-bullseye \
   sh -c 'export PGPASSWORD=eventuate; exec psql -h database -U eventuate'
