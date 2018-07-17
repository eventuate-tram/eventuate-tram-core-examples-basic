CREATE DATABASE IF NOT EXISTS custom_eventuate_schema;
GRANT ALL PRIVILEGES ON custom_eventuate_schema.* TO 'mysqluser'@'%' WITH GRANT OPTION;

USE custom_eventuate_schema;

DROP Table IF Exists message;
DROP Table IF Exists received_messages;
DROP Table IF Exists aggregate_instance_subscriptions;
DROP Table IF Exists saga_instance;

CREATE TABLE message (
  id VARCHAR(1000) PRIMARY KEY,
  destination VARCHAR(1000) NOT NULL,
  headers VARCHAR(1000) NOT NULL,
  payload VARCHAR(1000) NOT NULL,
  published SMALLINT DEFAULT 0
);

CREATE INDEX message_published_idx ON message(published, id);

CREATE TABLE received_messages (
  consumer_id VARCHAR(1000),
  message_id VARCHAR(1000),
  PRIMARY KEY(consumer_id, message_id)
);
