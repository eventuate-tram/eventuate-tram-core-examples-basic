package io.eventuate.tram.examples.basic.inmemory.events;

import io.eventuate.tram.examples.basic.events.AbstractTramEventTest;
import io.eventuate.tram.examples.basic.events.AbstractTramEventTestConfiguration;
import io.eventuate.tram.spring.inmemory.TramInMemoryConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InMemoryTramEventTest.Config.class)
public class InMemoryTramEventTest extends AbstractTramEventTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({
            AbstractTramEventTestConfiguration.class,
            TramInMemoryConfiguration.class
    })
    static class Config {
    }
}
