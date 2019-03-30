package io.eventuate.tram.examples.basic.commands;

import io.eventuate.tram.commands.common.ReplyMessageHeaders;
import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Send a command message to TramCommandTestCommandHandler and handle the reply
 */
public abstract class AbstractTramCommandTest {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private CommandProducer commandProducer;

  @Autowired
  private  AbstractTramCommandTestConfig config;

  @Autowired
  private MessageConsumer messageConsumer;

  private BlockingQueue<Message> queue = new LinkedBlockingDeque<>();

  private Map<String, Long> fakeDataStore = new HashMap<>();

  @Test
  public void shouldInvokeCommandAndGetReply() throws InterruptedException {

    subscribeToReplyChannel();

    String commandMessageId = sendCommand();

    long timestamp = System.currentTimeMillis();

    logger.info("Persisting timestamp {} for command {}", timestamp, commandMessageId);

    fakeDataStore.put(commandMessageId, timestamp);

    assertReplyReceived(commandMessageId);

  }

  private void assertReplyReceived(String commandMessageId) throws InterruptedException {
    Message m = queue.poll(5, TimeUnit.SECONDS);
    assertNotNull(m);
    assertEquals(commandMessageId, m.getRequiredHeader(ReplyMessageHeaders.IN_REPLY_TO));
  }

  private String sendCommand() {
    return commandProducer.send(config.getCommandChannel(),
              new DoSomethingCommand(),
              config.getReplyChannel(),
              Collections.emptyMap());
  }

  private void subscribeToReplyChannel() {
    String subscriberId = "subscriberId" + config.getUniqueId();
    messageConsumer.subscribe(subscriberId, Collections.singleton(config.getReplyChannel()), this::handleReply);
  }

  private void handleReply(Message message) {
    logger.info("Got message {}", message);

    queue.add(message);

    Long timestamp = fakeDataStore.get(message.getRequiredHeader(ReplyMessageHeaders.IN_REPLY_TO));

    logger.info("The timestamp for this message is {}", timestamp);
  }
}
