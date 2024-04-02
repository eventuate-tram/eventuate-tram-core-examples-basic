package io.eventuate.tram.examples.basic.events.subscriber.common;

import io.eventuate.tram.examples.basic.events.subscriber.MessageHandler;
import io.eventuate.tram.messaging.common.Message;

import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AssertableMessageHandler {


    private final MessageHandler messageHandler;

    public AssertableMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }


    public BlockingQueue<Message> getQueue() {
        return messageHandler.getQueue();
    }

    public void assertEventPublished() {
        Message event = getQueue().poll();
        assertNotNull(event);
    }
}
