package io.eventuate.tram.examples.basic.events;

import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.spring.events.subscriber.TramEventSubscriberConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TramEventsPublisherConfiguration.class, TramEventSubscriberConfiguration.class})
public class AbstractTramEventTestConfiguration {

  @Bean
  public IdSupplier abstractTramEventTestConfig() {
    return new AbstractTramEventTestConfig();
  }

  @Bean
  public DomainEventDispatcher domainEventDispatcher(DomainEventDispatcherFactory domainEventDispatcherFactory,
                                                     IdSupplier idSupplier,
                                                     TramEventTestEventConsumer target) {
    return domainEventDispatcherFactory.make("eventDispatcherId" + idSupplier.get(),
            target.domainEventHandlers());
  }

  @Bean
  public TramEventTestEventConsumer tramEventTestTarget(AggregateSupplier aggregateSupplier) {
    return new TramEventTestEventConsumer(aggregateSupplier.getAggregateType());
  }


}
