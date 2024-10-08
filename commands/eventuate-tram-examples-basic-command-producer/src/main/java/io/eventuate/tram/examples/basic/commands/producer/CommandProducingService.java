package io.eventuate.tram.examples.basic.commands.producer;


import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.examples.basic.commands.common.CommandConfigurationProperties;
import io.eventuate.tram.examples.basic.commands.common.ReserveCreditCommand;
import io.eventuate.tram.messaging.common.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CommandProducingService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CommandProducer commandProducer;
    private final CommandReplyHandler commandReplyHandler;
    private final CommandConfigurationProperties commandConfigurationProperties;

    public CommandProducingService(CommandProducer commandProducer, CommandReplyHandler commandReplyHandler, CommandConfigurationProperties commandConfigurationProperties) {
        this.commandProducer = commandProducer;
        this.commandReplyHandler = commandReplyHandler;
        this.commandConfigurationProperties = commandConfigurationProperties;
    }

    String produceCommand(ProduceRequest produceRequest) {
        logger.info("sending {}", produceRequest);
        var messageId = commandProducer.send(commandChannel(), new ReserveCreditCommand(produceRequest.customerId()), replyChannel(), Collections.emptyMap());
        logger.info("sent {}", produceRequest);
        commandReplyHandler.noteCommandSent(messageId);
        return messageId;
    }

    public String commandChannel() {
        return commandConfigurationProperties.getCommandChannel();
    }

    public String replyChannel() {
        return commandConfigurationProperties.getReplyChannel();
    }

    public List<Message> getReplies() {
        return commandReplyHandler.getReplies();
    }
}
