package io.eventuate.tram.examples.basic.events;

import io.eventuate.tram.events.spring.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.events.spring.subscriber.TramEventSubscriberConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TramEventsPublisherConfiguration.class, TramEventSubscriberConfiguration.class})
public class AbstractTramEventTestConfiguration {

  @Bean
  public AbstractTramEventTestConfig abstractTramEventTestConfig() {
    return new AbstractTramEventTestConfig();
  }

  @Bean
  public DomainEventDispatcher domainEventDispatcher(DomainEventDispatcherFactory domainEventDispatcherFactory,
                                                     AbstractTramEventTestConfig config,
                                                     TramEventTestEventConsumer target) {
    return domainEventDispatcherFactory.make("eventDispatcherId" + config.getUniqueId(), target.domainEventHandlers());
  }

  @Bean
  public TramEventTestEventConsumer tramEventTestTarget(AbstractTramEventTestConfig config) {
    return new TramEventTestEventConsumer(config.getAggregateType());
  }


}
