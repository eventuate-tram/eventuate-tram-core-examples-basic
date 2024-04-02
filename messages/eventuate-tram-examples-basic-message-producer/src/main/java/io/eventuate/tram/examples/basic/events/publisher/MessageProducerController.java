package io.eventuate.tram.examples.basic.events.publisher;

import io.eventuate.tram.examples.basic.commands.common.MessageConfigurationProperties;
import io.eventuate.tram.messaging.producer.MessageBuilder;
import io.eventuate.tram.messaging.producer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageProducerController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MessageProducer messageProducer;
    private final MessageConfigurationProperties messageConfigurationProperties;

    @Autowired
    public MessageProducerController(MessageProducer messageProducer, MessageConfigurationProperties messageConfigurationProperties) {
        this.messageProducer = messageProducer;
        this.messageConfigurationProperties = messageConfigurationProperties;
    }

    @PostMapping("/produce")
    public void produce(@RequestBody ProduceRequest produceRequest) {
        logger.info("Producing {}", produceRequest);
        messageProducer.send(messageConfigurationProperties.getChannel(), MessageBuilder.withPayload(String.format("Message: %s", produceRequest.accountId())).build());
        logger.info("produced {}", produceRequest);
    }

}
