package io.eventuate.tram.examples.basic.kafka.commands;

import io.eventuate.tram.examples.basic.commands.AbstractTramCommandTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = JdbcKafkaTramCommandTestConfiguration.class)
public class JdbcKafkaTramCommandTest extends AbstractTramCommandTest {
}
