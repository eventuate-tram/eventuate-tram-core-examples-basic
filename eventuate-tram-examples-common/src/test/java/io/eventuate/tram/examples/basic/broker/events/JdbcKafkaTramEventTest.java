package io.eventuate.tram.examples.basic.broker.events;

import io.eventuate.tram.examples.basic.events.AbstractTramEventTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcKafkaTramEventTestConfiguration.class)
public class JdbcKafkaTramEventTest extends AbstractTramEventTest {
}
