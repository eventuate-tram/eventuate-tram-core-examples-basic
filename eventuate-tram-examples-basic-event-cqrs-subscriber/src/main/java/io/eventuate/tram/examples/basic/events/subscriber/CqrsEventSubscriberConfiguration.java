package io.eventuate.tram.examples.basic.events.subscriber;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.spring.consumer.common.TramNoopDuplicateMessageDetectorConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TramNoopDuplicateMessageDetectorConfiguration.class)
public class CqrsEventSubscriberConfiguration {

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
