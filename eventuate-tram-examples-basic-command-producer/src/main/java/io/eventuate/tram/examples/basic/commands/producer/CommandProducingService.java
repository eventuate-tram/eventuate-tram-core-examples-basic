package io.eventuate.tram.examples.basic.commands.producer;


import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.examples.basic.commands.common.ReserveCreditCommand;
import io.eventuate.tram.examples.basic.commands.common.CommandConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CommandProducingService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CommandProducer commandProducer;
    private final CommandConfigurationProperties commandConfigurationProperties;

    @Autowired
    public CommandProducingService(CommandProducer commandProducer, CommandConfigurationProperties commandConfigurationProperties) {
        this.commandProducer = commandProducer;
        this.commandConfigurationProperties = commandConfigurationProperties;
    }

    String produceCommand(ProduceRequest produceRequest) {
        logger.info("sending {}", produceRequest);
        var messageId = commandProducer.send(commandChannel(), new ReserveCreditCommand(produceRequest.customerId()), replyChannel(), Collections.emptyMap());
        logger.info("sent {}", produceRequest);
        return messageId;
    }

    public String commandChannel() {
        return commandConfigurationProperties.getCommandChannel();
    }

    public String replyChannel() {
        return commandConfigurationProperties.getReplyChannel();
    }
}
