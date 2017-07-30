package io.eventuate.tram.examples.basic.commands;

import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.tram.messaging.producer.MessageProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collections;

@Configuration
@Import(TramCommandProducerConfiguration.class)
public class AbstractTramCommandTestConfiguration {

  @Bean
  public AbstractTramCommandTestConfig abstractTramCommandTestConfig() {
    return  new AbstractTramCommandTestConfig();

  }

  @Bean
  public AbstractTramCommandTestCommandHandler abstractTramCommandTestTarget() {
    return new AbstractTramCommandTestCommandHandler();
  }

  @Bean
  public CommandDispatcher commandDispatcher(AbstractTramCommandTestConfig config, AbstractTramCommandTestCommandHandler target,
                                             ChannelMapping channelMapping,
                                             MessageConsumer messageConsumer,
                                             MessageProducer messageProducer) {

    return new CommandDispatcher(config.getCommandDispatcheId(), target, config.getCommandChannel(), channelMapping, messageConsumer, messageProducer);
  }

  @Bean
  public ChannelMapping channelMapping(AbstractTramCommandTestConfig config) {
    return new DefaultChannelMapping(Collections.singletonMap("CustomerAggregate", config.getCustomerChannel()));
  }
}
