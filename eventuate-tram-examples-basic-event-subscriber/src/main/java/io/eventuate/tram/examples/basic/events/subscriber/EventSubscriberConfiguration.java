package io.eventuate.tram.examples.basic.events.subscriber;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventSubscriberConfiguration {

    @Bean
    public DomainEventDispatcher domainEventDispatcher(DomainEventDispatcherFactory domainEventDispatcherFactory,
                                                       AccountEventsConsumer target) {
        return domainEventDispatcherFactory.make("eventSubscriber",
                target.domainEventHandlers());
    }

    @Bean
    public AccountEventsConsumer accountEventsConsumer() {
        return new AccountEventsConsumer();
    }

}
