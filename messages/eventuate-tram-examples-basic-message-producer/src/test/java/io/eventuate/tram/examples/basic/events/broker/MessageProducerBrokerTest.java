package io.eventuate.tram.examples.basic.events.broker;

import io.eventuate.tram.examples.basic.commands.common.MessageConfigurationProperties;
import io.eventuate.tram.examples.basic.events.publisher.MessageProducerConfiguration;
import io.eventuate.tram.examples.basic.events.publisher.ProduceRequest;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import static io.restassured.RestAssured.given;

@SpringBootTest(classes = MessageProducerBrokerTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"message.channel=message-${random.value}"})
public class MessageProducerBrokerTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({MessageProducerConfiguration.class, EventOutboxTestSupportConfiguration.class})
    public static class Config {

        @Bean
        MessageOutboxTestSupport messageOutboxTestSupport(JdbcTemplate jdbcTemplate) {
            return new MessageOutboxTestSupport(jdbcTemplate);
        }
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Autowired
    private MessageOutboxTestSupport messageOutboxTestSupport;

    @Autowired
    private MessageConfigurationProperties messageConfigurationProperties;


    @Test
    public void shouldProduceMessage() {

        long accountId = System.currentTimeMillis();

        given().when()
                .log().all()
                .body(new ProduceRequest(accountId))
                .contentType(ContentType.JSON)
                .post("/produce")
                .then()
                .statusCode(200);

        Eventually.eventually(() -> {
            messageOutboxTestSupport.assertMessageProduced(messageConfigurationProperties.getChannel());
        });

    }

}
