package io.eventuate.tram.examples.basic.commands.inmemory;

import io.eventuate.tram.commands.common.CommandReplyOutcome;
import io.eventuate.tram.commands.common.ReplyMessageHeaders;
import io.eventuate.tram.examples.basic.commands.consumer.CommandConsumerConfiguration;
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


@SpringBootTest(classes = CommandConsumerInMemoryTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
            properties = {"command.commandChannel=command-${random.value}",
                          "command.replyChannel=reply-${random.value}"})
public class CommandConsumerInMemoryTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({
            CommandProducerConfiguration.class,
            CommandConsumerConfiguration.class,
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
    public void shouldHandleCommand() throws InterruptedException {
        assertableMessageConsumer.subscribe(commandProducingService.replyChannel());

        String customerId = Long.toString(System.currentTimeMillis());

        String messageId = RestAssured.given().when()
                .log().all()
                .body(new ProduceRequest(customerId))
                .contentType(ContentType.JSON)
                .post("/send")
                .then()
                .statusCode(200)
                .extract().path("messageId")
        ;

        assertableMessageConsumer
                .assertMessageReceived(commandProducingService.replyChannel())
                .header(ReplyMessageHeaders.IN_REPLY_TO, messageId)
                .header(ReplyMessageHeaders.REPLY_OUTCOME, CommandReplyOutcome.SUCCESS.name());

    }
}
