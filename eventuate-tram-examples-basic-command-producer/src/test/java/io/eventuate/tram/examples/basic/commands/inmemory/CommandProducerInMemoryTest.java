package io.eventuate.tram.examples.basic.commands.inmemory;

import io.eventuate.tram.commands.common.CommandMessageHeaders;
import io.eventuate.tram.examples.basic.commands.common.ReserveCreditCommand;
import io.eventuate.tram.examples.basic.commands.producer.CommandProducerConfiguration;
import io.eventuate.tram.examples.basic.commands.producer.CommandProducingService;
import io.eventuate.tram.examples.basic.commands.producer.ProduceRequest;
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

import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(classes = CommandProducerInMemoryTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommandProducerInMemoryTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({
            CommandProducerConfiguration.class,
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
    private CommandProducingService commandProducingService;

    @Test
    public void shouldPublishEvent() throws InterruptedException {
        assertableMessageConsumer.subscribe(commandProducingService.commandChannel());

        String customerId = Long.toString(System.currentTimeMillis());

        RestAssured.given().when()
                .log().all()
                .body(new ProduceRequest(customerId))
                .contentType(ContentType.JSON)
                .post("/send")
                .then()
                .statusCode(200);
        ;

        assertableMessageConsumer
                .assertMessageReceived(commandProducingService.commandChannel())
                .header(CommandMessageHeaders.COMMAND_TYPE, ReserveCreditCommand.class.getName())
                .payload("customerId", equalTo(customerId));

    }
}
