package io.eventuate.tram.examples.basic.events.subscriber.common;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.examples.basic.events.domain.AccountDebited;
import io.eventuate.tram.examples.basic.events.subscriber.CqrsAccountEventsConsumer;

import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AssertableAccountEventsConsumer {


    private final CqrsAccountEventsConsumer accountEventsConsumer;

    public AssertableAccountEventsConsumer(CqrsAccountEventsConsumer accountEventsConsumer) {
        this.accountEventsConsumer = accountEventsConsumer;
    }


    public BlockingQueue<DomainEventEnvelope<AccountDebited>> getQueue() {
        return accountEventsConsumer.getQueue();
    }

    public void assertEventPublished(String aggregateId) {
        DomainEventEnvelope<AccountDebited> event = getQueue().poll();
        assertNotNull(event);
        assertEquals(aggregateId, event.getAggregateId());
    }
}
