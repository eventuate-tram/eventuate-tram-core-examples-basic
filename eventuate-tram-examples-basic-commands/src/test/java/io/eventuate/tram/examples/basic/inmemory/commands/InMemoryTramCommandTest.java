package io.eventuate.tram.examples.basic.inmemory.commands;

import io.eventuate.tram.examples.basic.commands.AbstractTramCommandTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InMemoryTramCommandTestConfiguration.class)
public class InMemoryTramCommandTest extends AbstractTramCommandTest {
}
