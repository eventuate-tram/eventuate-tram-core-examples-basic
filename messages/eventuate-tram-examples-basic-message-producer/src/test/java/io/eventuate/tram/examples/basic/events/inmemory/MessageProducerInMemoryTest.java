package io.eventuate.tram.examples.basic.events.inmemory;

import io.eventuate.tram.examples.basic.commands.common.MessageConfigurationProperties;
import io.eventuate.tram.examples.basic.events.publisher.MessageProducerConfiguration;
import io.eventuate.tram.examples.basic.events.publisher.ProduceRequest;
import io.eventuate.tram.spring.inmemory.TramInMemoryConfiguration;
import io.eventuate.tram.spring.testing.messaging.consumer.AssertableMessage;
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


@SpringBootTest(classes = MessageProducerInMemoryTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageProducerInMemoryTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({
            MessageProducerConfiguration.class,
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

    @Autowired
    private MessageConfigurationProperties messageConfigurationProperties;

    @Test
    public void shouldProduceMessage() throws InterruptedException {
        assertableMessageConsumer.subscribe(messageConfigurationProperties.getChannel());

        var produceRequest = new ProduceRequest(101L);

        given().when()
                .log().all()
                .body(produceRequest)
                .contentType(ContentType.JSON)
                .post("/produce")
                .then()
                .statusCode(200);
        ;

        AssertableMessage assertableMessage = assertableMessageConsumer
                .assertMessageReceived(messageConfigurationProperties.getChannel());
                // .payload(equalTo(String.format("Message: %s", produceRequest.accountId())));

    }
}
