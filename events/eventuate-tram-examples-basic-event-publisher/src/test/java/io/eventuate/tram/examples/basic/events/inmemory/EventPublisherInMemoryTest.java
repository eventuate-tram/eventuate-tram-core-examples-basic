package io.eventuate.tram.examples.basic.events.inmemory;

import io.eventuate.tram.events.common.EventMessageHeaders;
import io.eventuate.tram.examples.basic.events.domain.AccountDebited;
import io.eventuate.tram.examples.basic.events.publisher.EventPublisherConfiguration;
import io.eventuate.tram.examples.basic.events.publisher.PublishRequest;
import io.eventuate.tram.spring.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.spring.testing.messaging.consumer.AssertableMessageConsumer;
import io.eventuate.tram.spring.testing.messaging.consumer.AssertableMessageConsumerConfiguration;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(classes = EventPublisherInMemoryTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventPublisherInMemoryTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({
            EventPublisherConfiguration.class,
            TramInMemoryConfiguration.class,
            AssertableMessageConsumerConfiguration.class
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
    private AssertableMessageConsumer assertableMessageConsumer;

    @Test
    public void shouldPublishEvent() throws InterruptedException {
        assertableMessageConsumer.subscribe("Account");

        given().when()
                .log().all()
                .body(new PublishRequest(101L, 102L))
                .contentType(ContentType.JSON)
                .post("/publish")
                .then()
                .statusCode(200);
        ;

        assertableMessageConsumer
                .assertMessageReceived("Account")
                .header(EventMessageHeaders.EVENT_TYPE, AccountDebited.class.getName())
                .header(EventMessageHeaders.AGGREGATE_ID, "101")
                .payload("amount", equalTo(102));

    }
}
