package io.eventuate.tram.examples.basic.events.subscriber.inmemory;

import io.eventuate.tram.examples.basic.events.publisher.MessageProducerConfiguration;
import io.eventuate.tram.examples.basic.events.publisher.ProduceRequest;
import io.eventuate.tram.examples.basic.events.subscriber.MessageConsumerConfiguration;
import io.eventuate.tram.spring.inmemory.TramInMemoryConfiguration;
import io.eventuate.util.test.async.Eventually;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;


@SpringBootTest(classes = MessageConsumerInMemoryTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageConsumerInMemoryTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({
            MessageProducerConfiguration.class, MessageConsumerConfiguration.class,
            TramInMemoryConfiguration.class})
    static class Config {
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }


    @Test
    public void shouldConsumeMessage() {

        long accountId = System.currentTimeMillis();

        given().when()
                .log().all()
                .body(new ProduceRequest(accountId))
                .contentType(ContentType.JSON)
                .post("/produce")
                .then()
                .statusCode(200);

        Eventually.eventually(100, 500, TimeUnit.MILLISECONDS, () -> {
            given().when()
                    .log().all()
                    .get("/messages?accountId=" + accountId)
                    .then()
                    .statusCode(200);
        });

    }


}
