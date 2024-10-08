package io.eventuate.tram.examples.basic.events.subscriber;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.examples.basic.events.common.EventConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EventConfigurationProperties.class)
@ComponentScan
public class EventSubscriberConfiguration {

    @Bean
    public DomainEventDispatcher domainEventDispatcher(DomainEventDispatcherFactory domainEventDispatcherFactory,
                                                       AccountEventsConsumer target) {
        return domainEventDispatcherFactory.make("eventSubscriber",
                target.domainEventHandlers());
    }

}
