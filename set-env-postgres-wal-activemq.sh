. ./_set-env.sh

export ACTIVEMQ_URL=tcp://${DOCKER_HOST_IP}:61616
export SPRING_DATASOURCE_URL=jdbc:postgresql://${DOCKER_HOST_IP}/eventuate
export SPRING_DATASOURCE_USERNAME=eventuate
export SPRING_DATASOURCE_PASSWORD=eventuate
export SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
export SPRING_PROFILES_ACTIVE=PostgresWal,ActiveMQ
