package io.eventuate.tram.examples.basic.inmemory.events;

import io.eventuate.tram.examples.basic.events.AbstractTramEventTestConfiguration;
import io.eventuate.tram.spring.inmemory.TramInMemoryConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({AbstractTramEventTestConfiguration.class, TramInMemoryConfiguration.class})
class JdbcKafkaTramEventTestConfiguration {
}
