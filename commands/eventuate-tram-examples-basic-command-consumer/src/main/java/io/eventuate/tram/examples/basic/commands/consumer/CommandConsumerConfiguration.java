package io.eventuate.tram.examples.basic.commands.consumer;

import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.commands.consumer.CommandDispatcherFactory;
import io.eventuate.tram.examples.basic.commands.common.CommandConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CommandConfigurationProperties.class)
public class CommandConsumerConfiguration {

  @Bean
  public CreditManagementCommandHandlers creditManagementCommandHandlers(CommandConfigurationProperties commandConfigurationProperties) {
    return new CreditManagementCommandHandlers(commandConfigurationProperties.getCommandChannel());
  }

  @Bean
  public CommandDispatcher commandDispatcher(CommandDispatcherFactory commandDispatcherFactory, CreditManagementCommandHandlers creditManagementCommandHandlers) {
    return commandDispatcherFactory.make("command-dispatcher-" + System.currentTimeMillis(), creditManagementCommandHandlers.getCommandHandlers());
  }

}
