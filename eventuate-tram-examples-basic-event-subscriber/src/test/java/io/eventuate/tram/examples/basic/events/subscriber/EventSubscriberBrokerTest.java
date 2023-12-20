package io.eventuate.tram.examples.basic.events.subscriber;

import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.examples.basic.events.domain.AccountDebited;
import io.eventuate.tram.examples.basic.events.publisher.EventPublisherConfiguration;
import io.eventuate.tram.examples.basic.events.publisher.PublishRequest;
import io.eventuate.util.test.async.Eventually;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventSubscriberBrokerTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventSubscriberBrokerTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({EventPublisherConfiguration.class, EventSubscriberConfiguration.class})
    public static class Config {
    }

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Autowired
    private AccountEventsConsumer accountEventsConsumer;

    @Test
    public void shouldPublishEvent() throws InterruptedException {

        long accountId = System.currentTimeMillis();

        given().when()
                .log().all()
                .body(new PublishRequest(accountId, 102L))
                .contentType(ContentType.JSON)
                .post("/publish")
                .then()
                .statusCode(200);

        Eventually.eventually(100, 500, TimeUnit.MILLISECONDS, () -> {
            assertEventPublished(Long.toString(accountId));
        });

    }

    public void assertEventPublished(String aggregateId) {
        DomainEventEnvelope<AccountDebited> event;
        event = accountEventsConsumer.getQueue().poll();
        assertNotNull(event);
        assertEquals(aggregateId, event.getAggregateId());
    }



}
