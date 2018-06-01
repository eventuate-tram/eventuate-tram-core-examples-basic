package io.eventuate.tram.examples.basic.rabbitmq.messages;

import io.eventuate.tram.examples.basic.messages.AbstractTramMessageTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcRabbitMQTramMessageTestConfiguration.class)
public class JdbcRabbitMQTramMessageTest extends AbstractTramMessageTest {
}
