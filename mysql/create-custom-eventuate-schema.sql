CREATE DATABASE IF NOT EXISTS custom_eventuate_schema;
GRANT ALL PRIVILEGES ON custom_eventuate_schema.* TO 'mysqluser'@'%' WITH GRANT OPTION;

USE custom_eventuate_schema;

CREATE TABLE message (
  id VARCHAR(767) PRIMARY KEY,
  destination VARCHAR(1000) NOT NULL,
  headers VARCHAR(1000) NOT NULL,
  payload VARCHAR(1000) NOT NULL,
  published SMALLINT DEFAULT 0,
  creation_time BIGINT
);

CREATE INDEX message_published_idx ON message(published, id);

CREATE TABLE received_messages (
  consumer_id VARCHAR(767),
  message_id VARCHAR(767),
  PRIMARY KEY(consumer_id, message_id),
  creation_time BIGINT
);

