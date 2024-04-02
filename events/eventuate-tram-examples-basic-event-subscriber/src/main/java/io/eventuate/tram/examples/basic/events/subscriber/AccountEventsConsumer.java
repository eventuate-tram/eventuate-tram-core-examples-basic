package io.eventuate.tram.examples.basic.events.subscriber;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import io.eventuate.tram.examples.basic.events.common.EventConfigurationProperties;
import io.eventuate.tram.examples.basic.events.domain.AccountDebited;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class AccountEventsConsumer {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final BlockingQueue<DomainEventEnvelope<AccountDebited>> queue = new LinkedBlockingDeque<>();

  @Autowired
  private EventConfigurationProperties eventConfigurationProperties;

  public AccountEventsConsumer() {
  }

  public DomainEventHandlers domainEventHandlers() {
    return DomainEventHandlersBuilder
            .forAggregateType("Account" + eventConfigurationProperties.getAggregateSuffix())
            .onEvent(AccountDebited.class, this::handleAccountDebited)
            .build();
  }

  public void handleAccountDebited(DomainEventEnvelope<AccountDebited> event) {
    logger.info("Got event {}", event);
    queue.add(event);
  }

  public BlockingQueue<DomainEventEnvelope<AccountDebited>> getQueue() {
    return queue;
  }
}
