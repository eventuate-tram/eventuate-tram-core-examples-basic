package io.eventuate.tram.examples.basic.commands;

import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.commands.consumer.CommandDispatcherFactory;
import io.eventuate.tram.commands.spring.consumer.TramCommandConsumerConfiguration;
import io.eventuate.tram.commands.spring.producer.TramCommandProducerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TramCommandProducerConfiguration.class, TramCommandConsumerConfiguration.class})
public class AbstractTramCommandTestConfiguration {

  @Bean
  public AbstractTramCommandTestConfig abstractTramCommandTestConfig() {
    return  new AbstractTramCommandTestConfig();

  }

  @Bean
  public TramCommandTestCommandHandler abstractTramCommandTestTarget(AbstractTramCommandTestConfig config) {
    return new TramCommandTestCommandHandler(config.getCommandChannel());
  }

  @Bean
  public CommandDispatcher commandDispatcher(CommandDispatcherFactory commandDispatcherFactory,
                                             AbstractTramCommandTestConfig config,
                                             TramCommandTestCommandHandler target) {
    return commandDispatcherFactory.make(config.getCommandDispatcheId(), target.getCommandHandlers());
  }

}
