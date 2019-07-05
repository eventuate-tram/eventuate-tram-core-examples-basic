package io.eventuate.tram.examples.basic.rabbitmq.events;

import io.eventuate.jdbcrabbitmq.TramJdbcRabbitMQConfiguration;
import io.eventuate.tram.examples.basic.events.AbstractTramEventTestConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({AbstractTramEventTestConfiguration.class, TramJdbcRabbitMQConfiguration.class})
public class JdbcRabbitMQTramEventTestConfiguration {
}
