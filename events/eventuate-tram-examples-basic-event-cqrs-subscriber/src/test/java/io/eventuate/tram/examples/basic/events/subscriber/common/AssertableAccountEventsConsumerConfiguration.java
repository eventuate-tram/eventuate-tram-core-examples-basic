package io.eventuate.tram.examples.basic.events.subscriber.common;

import io.eventuate.tram.examples.basic.events.subscriber.CqrsAccountEventsConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssertableAccountEventsConsumerConfiguration {
    @Bean
    public AssertableAccountEventsConsumer assertableAccountEventsConsumer(CqrsAccountEventsConsumer accountEventsConsumer) {
        return new AssertableAccountEventsConsumer(accountEventsConsumer);
    }

}
