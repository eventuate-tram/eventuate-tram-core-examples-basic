package io.eventuate.tram.examples.basic.commands.broker;

import io.eventuate.tram.commands.common.CommandReplyOutcome;
import io.eventuate.tram.commands.common.ReplyMessageHeaders;
import io.eventuate.tram.examples.basic.commands.consumer.CommandConsumerConfiguration;
import io.eventuate.tram.examples.basic.commands.producer.CommandProducerConfiguration;
import io.eventuate.tram.examples.basic.commands.producer.CommandProducingService;
import io.eventuate.tram.examples.basic.commands.producer.CommandReplyHandler;
import io.eventuate.tram.examples.basic.commands.producer.ProduceRequest;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.spring.testing.messaging.consumer.AssertableMessageConsumer;
import io.eventuate.tram.spring.testing.messaging.consumer.AssertableMessageConsumerConfiguration;
import io.eventuate.util.test.async.Eventually;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = CommandConsumerBrokerTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"command.commandChannel=command-${random.value}",
                "command.replyChannel=reply-${random.value}"})
public class CommandConsumerBrokerTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({CommandProducerConfiguration.class, CommandConsumerConfiguration.class,
            AssertableMessageConsumerConfiguration.class
    })
    public static class Config {

    }

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Autowired
    private CommandProducingService commandProducingService;

    @Autowired
    private CommandReplyHandler commandReplyHandler;

    @Test
    public void shouldHandleCommand() {


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

        Eventually.eventually(() -> {
            Message reply = commandReplyHandler.getQueue().poll();
            Assertions.assertNotNull(reply);
            Assertions.assertEquals(messageId, reply.getRequiredHeader(ReplyMessageHeaders.IN_REPLY_TO));

        });
    }

}
