package io.eventuate.tram.examples.basic.rabbitmq.commands;

import io.eventuate.tram.examples.basic.commands.AbstractTramCommandTestConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({AbstractTramCommandTestConfiguration.class, })
public class JdbcRabbitMQTramCommandTestConfiguration {
}
