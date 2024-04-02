package io.eventuate.tram.examples.basic.events.subscriber.inmemory;

import io.eventuate.tram.examples.basic.events.publisher.EventPublisherConfiguration;
import io.eventuate.tram.examples.basic.events.publisher.PublishRequest;
import io.eventuate.tram.examples.basic.events.subscriber.EventSubscriberConfiguration;
import io.eventuate.tram.examples.basic.events.subscriber.common.AssertableAccountEventsConsumer;
import io.eventuate.tram.examples.basic.events.subscriber.common.AssertableAccountEventsConsumerConfiguration;
import io.eventuate.tram.spring.inmemory.TramInMemoryConfiguration;
import io.eventuate.util.test.async.Eventually;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;


@SpringBootTest(classes = EventSubscriberInMemoryTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventSubscriberInMemoryTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({
            EventPublisherConfiguration.class, EventSubscriberConfiguration.class,
            TramInMemoryConfiguration.class,
            AssertableAccountEventsConsumerConfiguration.class,
    })
    static class Config {
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }


    @Autowired
    private AssertableAccountEventsConsumer accountEventsConsumer;

    @Test
    public void shouldPublishEvent() {

        long accountId = System.currentTimeMillis();

        given().when()
                .log().all()
                .body(new PublishRequest(accountId, 102L))
                .contentType(ContentType.JSON)
                .post("/publish")
                .then()
                .statusCode(200);

        Eventually.eventually(100, 500, TimeUnit.MILLISECONDS, () -> {
            accountEventsConsumer.assertEventPublished(Long.toString(accountId));
        });

    }


}
