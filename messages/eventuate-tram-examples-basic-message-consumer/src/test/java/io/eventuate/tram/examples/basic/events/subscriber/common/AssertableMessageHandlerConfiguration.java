package io.eventuate.tram.examples.basic.events.subscriber.common;

import io.eventuate.tram.examples.basic.events.subscriber.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssertableMessageHandlerConfiguration {

    @Bean
    public AssertableMessageHandler assertMessageHandler(MessageHandler messageHandler) {
        return new AssertableMessageHandler(messageHandler);
    }

}
