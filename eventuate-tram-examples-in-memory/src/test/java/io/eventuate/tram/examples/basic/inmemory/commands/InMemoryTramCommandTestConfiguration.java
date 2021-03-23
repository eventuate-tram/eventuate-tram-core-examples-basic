package io.eventuate.tram.examples.basic.inmemory.commands;

import io.eventuate.common.spring.jdbc.EventuateTransactionTemplateConfiguration;
import io.eventuate.tram.examples.basic.commands.AbstractTramCommandTestConfiguration;
import io.eventuate.tram.spring.inmemory.TramInMemoryConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({
        AbstractTramCommandTestConfiguration.class,
        TramInMemoryConfiguration.class,
        EventuateTransactionTemplateConfiguration.class
})
public class InMemoryTramCommandTestConfiguration {
}
