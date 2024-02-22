package io.eventuate.tram.examples.basic.commands.broker;

import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.commands.common.CommandMessageHeaders;
import io.eventuate.tram.examples.basic.commands.common.ReserveCreditCommand;
import io.eventuate.tram.examples.basic.commands.producer.CommandProducerConfiguration;
import io.eventuate.tram.examples.basic.commands.producer.CommandProducingService;
import io.eventuate.tram.examples.basic.commands.producer.ProduceRequest;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.producer.MessageBuilder;
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

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
    private CommandProducingService commandProducingService;

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

        assertCommandMessageSent(commandProducingService.commandChannel());
    }

    public void assertCommandMessageSent(String channel) {

        List<Message> messages = jdbcTemplate.query("select headers,payload from message where destination = ?", (rs, rowNum) -> {
            String headers = rs.getString("headers");
            String payload = rs.getString("payload");
            return MessageBuilder.withPayload(payload).withExtraHeaders("", JSonMapper.fromJson(headers, Map.class)).build();
        }, channel);

        assertThat(messages)
                .hasSize(1)
                .allMatch(reply -> ReserveCreditCommand.class.getName().equals(reply.getRequiredHeader(CommandMessageHeaders.COMMAND_TYPE)));
    }

}
