package io.eventuate.tram.examples.basic.kafka.commands;

import io.eventuate.tram.examples.basic.commands.AbstractTramCommandTestConfiguration;
import io.eventuate.tram.jdbckafka.TramJdbcKafkaConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({AbstractTramCommandTestConfiguration.class, TramJdbcKafkaConfiguration.class, })
public class JdbcKafkaTramCommandTestConfiguration {
}
