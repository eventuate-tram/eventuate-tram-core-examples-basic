package io.eventuate.tram.examples.basic.inmemory.messages;

import io.eventuate.common.spring.jdbc.EventuateTransactionTemplateConfiguration;
import io.eventuate.tram.spring.inmemory.TramInMemoryConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({TramInMemoryConfiguration.class, EventuateTransactionTemplateConfiguration.class})
public class InMemoryTramMessageTestConfiguration {
}
