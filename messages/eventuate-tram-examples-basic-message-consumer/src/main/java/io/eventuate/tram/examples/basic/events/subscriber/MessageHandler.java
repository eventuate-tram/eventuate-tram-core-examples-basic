package io.eventuate.tram.examples.basic.events.subscriber;

import io.eventuate.tram.examples.basic.commands.common.MessageConfigurationProperties;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MessageHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final BlockingQueue<Message> queue = new LinkedBlockingDeque<>();
  private final MessageConsumer messageConsumer;
  private final MessageConfigurationProperties messageConfigurationProperties;

  public MessageHandler(MessageConsumer messageConsumer, MessageConfigurationProperties messageConfigurationProperties) {
    this.messageConsumer = messageConsumer;
    this.messageConfigurationProperties = messageConfigurationProperties;
  }

  @PostConstruct
  public void subscribe() {
    messageConsumer.subscribe("messageConsumer-" + System.currentTimeMillis(), Set.of(messageConfigurationProperties.getChannel()), this::handleMessage);
  }

  public void handleMessage(Message event) {
    logger.info("Got event {}", event);
    queue.add(event);
  }

  public BlockingQueue<Message> getQueue() {
    return queue;
  }
}
