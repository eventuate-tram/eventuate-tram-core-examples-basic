package io.eventuate.tram.examples.basic.events;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandler;
import io.eventuate.tram.examples.basic.events.domain.AccountDebited;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class TramEventTestEventConsumer {

  private BlockingQueue<AccountDebited> queue = new LinkedBlockingDeque<>();

  @DomainEventHandler
  public void handleAccountDebited(DomainEventEnvelope<AccountDebited> event) {
    queue.add(event.getEvent());
  }

  public BlockingQueue<AccountDebited> getQueue() {
    return queue;
  }
}
