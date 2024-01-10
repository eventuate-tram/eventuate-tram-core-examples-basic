package io.eventuate.tram.examples.basic.events.subscriber.broker;

import io.eventuate.tram.examples.basic.events.publisher.EventPublisherConfiguration;
import io.eventuate.tram.examples.basic.events.publisher.PublishRequest;
import io.eventuate.tram.examples.basic.events.subscriber.EventSubscriberConfiguration;
import io.eventuate.tram.examples.basic.events.subscriber.common.AssertableAccountEventsConsumer;
import io.eventuate.tram.examples.basic.events.subscriber.common.AssertableAccountEventsConsumerConfiguration;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventSubscriberBrokerTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventSubscriberBrokerTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({EventPublisherConfiguration.class, EventSubscriberConfiguration.class, AssertableAccountEventsConsumerConfiguration.class})
    public static class Config {
    }

    @LocalServerPort
    private int port;

    @Before
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