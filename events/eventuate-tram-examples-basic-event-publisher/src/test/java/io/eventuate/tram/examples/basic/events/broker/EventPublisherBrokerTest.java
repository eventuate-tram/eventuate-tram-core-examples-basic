package io.eventuate.tram.examples.basic.events.broker;

import io.eventuate.tram.examples.basic.events.domain.AccountDebited;
import io.eventuate.tram.examples.basic.events.publisher.EventPublisherConfiguration;
import io.eventuate.tram.examples.basic.events.publisher.PublishRequest;
import io.eventuate.tram.spring.testing.events.publisher.EventOutboxTestSupport;
import io.eventuate.tram.spring.testing.events.publisher.EventOutboxTestSupportConfiguration;
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
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

@SpringBootTest(classes = EventPublisherBrokerTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:/application-test-broker.properties")
public class EventPublisherBrokerTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({EventPublisherConfiguration.class, EventOutboxTestSupportConfiguration.class})
    public static class Config {

    }

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Autowired
    private EventOutboxTestSupport eventOutboxTestSupport;

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

        Eventually.eventually(() -> {
            eventOutboxTestSupport.assertEventPublished("Account", Long.toString(accountId), AccountDebited.class.getName());
        });

    }

}
