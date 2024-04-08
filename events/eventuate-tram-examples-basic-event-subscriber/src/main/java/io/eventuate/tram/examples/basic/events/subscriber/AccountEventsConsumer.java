package io.eventuate.tram.examples.basic.events.subscriber;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import io.eventuate.tram.examples.basic.events.common.EventConfigurationProperties;
import io.eventuate.tram.examples.basic.events.domain.AccountDebited;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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

  public List<DomainEventEnvelope<AccountDebited>> getEventsFor(String aggregateId) {
    DomainEventEnvelope<AccountDebited> event;
    System.out.printf("looking for events for %s\n", aggregateId);
    while ((event = queue.poll()) != null) {
      System.out.printf("got event %s\n", event.getAggregateId());
      if (event.getAggregateId().equals(aggregateId)) {
        return List.of(event);
      }
    }
    return List.of();
  }
}
