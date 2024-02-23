package io.eventuate.tram.examples.basic.commands.broker;

import io.eventuate.tram.examples.basic.commands.common.CommandConfigurationProperties;
import io.eventuate.tram.examples.basic.commands.common.ReserveCreditCommand;
import io.eventuate.tram.examples.basic.commands.producer.CommandProducerConfiguration;
import io.eventuate.tram.examples.basic.commands.producer.ProduceRequest;
import io.eventuate.tram.spring.testing.outbox.commands.CommandOutboxTestSupport;
import io.eventuate.tram.spring.testing.outbox.commands.CommandOutboxTestSupportConfiguration;
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
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(classes = CommandProducerBrokerTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"command.commandChannel=command-${random.value}",
                "command.replyChannel=reply-${random.value}"})
public class CommandProducerBrokerTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({CommandProducerConfiguration.class, CommandOutboxTestSupportConfiguration.class})
    public static class Config {

    }

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Autowired
    private CommandOutboxTestSupport commandOutboxTestSupport;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommandConfigurationProperties commandConfigurationProperties;

    @Test
    public void shouldSendCommand() {

        String customerId = Long.toString(System.currentTimeMillis());

        RestAssured.given().when()
                .log().all()
                .body(new ProduceRequest(customerId))
                .contentType(ContentType.JSON)
                .post("/send")
                .then()
                .statusCode(200);

        commandOutboxTestSupport.assertCommandMessageSent(commandConfigurationProperties.getCommandChannel(), ReserveCreditCommand.class);
    }

}
