package io.eventuate.tram.examples.basic.events.publisher;

import io.eventuate.tram.examples.basic.commands.common.MessageConfigurationProperties;
import io.eventuate.tram.messaging.producer.MessageBuilder;
import io.eventuate.tram.messaging.producer.MessageProducer;
import org.springframework.stereotype.Component;

@Component
public class MessageProducingService {

    private final MessageProducer messageProducer;
    private final MessageConfigurationProperties messageConfigurationProperties;

    public MessageProducingService(MessageProducer messageProducer, MessageConfigurationProperties messageConfigurationProperties) {
        this.messageProducer = messageProducer;
        this.messageConfigurationProperties = messageConfigurationProperties;
    }

    void sendMessage(long accountId) {
        messageProducer.send(messageConfigurationProperties.getChannel(), MessageBuilder.withPayload(String.valueOf(accountId)).build());
    }
}
