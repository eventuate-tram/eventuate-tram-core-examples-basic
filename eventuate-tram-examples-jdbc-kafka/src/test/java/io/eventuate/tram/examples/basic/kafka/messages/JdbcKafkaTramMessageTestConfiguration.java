package io.eventuate.tram.examples.basic.kafka.messages;

import io.eventuate.tram.spring.jdbckafka.TramJdbcKafkaConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({TramJdbcKafkaConfiguration.class})
public class JdbcKafkaTramMessageTestConfiguration {
}
