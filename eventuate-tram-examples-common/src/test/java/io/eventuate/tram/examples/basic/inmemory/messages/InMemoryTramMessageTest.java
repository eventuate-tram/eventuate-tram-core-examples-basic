package io.eventuate.tram.examples.basic.inmemory.messages;

import io.eventuate.tram.examples.basic.messages.AbstractTramMessageTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InMemoryTramMessageTestConfiguration.class)
public class InMemoryTramMessageTest extends AbstractTramMessageTest {
}
