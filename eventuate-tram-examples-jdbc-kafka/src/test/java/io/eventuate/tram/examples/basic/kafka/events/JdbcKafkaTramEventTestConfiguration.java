package io.eventuate.tram.examples.basic.kafka.events;

import io.eventuate.tram.examples.basic.events.AbstractTramEventTestConfiguration;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({AbstractTramEventTestConfiguration.class, TramJdbcKafkaConfiguration.class})
public class JdbcKafkaTramEventTestConfiguration {
}
