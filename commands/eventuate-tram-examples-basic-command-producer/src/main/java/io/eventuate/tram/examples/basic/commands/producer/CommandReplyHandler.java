package io.eventuate.tram.examples.basic.commands.producer;

import io.eventuate.tram.commands.common.ReplyMessageHeaders;
import io.eventuate.tram.examples.basic.commands.common.CommandConfigurationProperties;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;

public class CommandReplyHandler {


    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BlockingQueue<Message> queue = new LinkedBlockingDeque<>();
    private final MessageConsumer messageConsumer;
    private final CommandConfigurationProperties messageConfigurationProperties;
    private final ConcurrentSkipListSet<String> commandMessageIds = new ConcurrentSkipListSet<>();
    private final List<Message> expectedReplies = new ArrayList<>();

    public CommandReplyHandler(MessageConsumer messageConsumer, CommandConfigurationProperties messageConfigurationProperties) {
        this.messageConsumer = messageConsumer;
        this.messageConfigurationProperties = messageConfigurationProperties;
    }

    @PostConstruct
    public void subscribe() {
        messageConsumer.subscribe("commandConsumer-" + System.currentTimeMillis(),
                Set.of(messageConfigurationProperties.getReplyChannel()), 
                this::handleMessage);
    }

    public void noteCommandSent(String commandId) {
        commandMessageIds.add(commandId);
    }
    
    public void handleMessage(Message message) {
        String replyTo = message.getRequiredHeader(ReplyMessageHeaders.IN_REPLY_TO);
        if (commandMessageIds.contains(replyTo)) {
            logger.info("Got EXPECTED reply {} to {}",
                message.getId(), replyTo);
            expectedReplies.add(message);
        } else
            logger.info("Got UNEXPECTED reply {} to {}!!!",
                message.getId(), replyTo);
        logger.info("Received reply {}", message);
        queue.add(message);
    }

    public BlockingQueue<Message> getQueue() {
        return queue;
    }

    public List<Message> getReplies() {
        return expectedReplies;
    }
}
