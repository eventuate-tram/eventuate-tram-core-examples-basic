package io.eventuate.tram.examples.basic.kafka.events;

import io.eventuate.tram.examples.basic.events.AbstractTramEventTestConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({AbstractTramEventTestConfiguration.class})
public class JdbcKafkaTramEventTestConfiguration {
}
