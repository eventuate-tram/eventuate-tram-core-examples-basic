package io.eventuate.tram.examples.basic.broker.events;

import io.eventuate.tram.examples.basic.events.AbstractTramEventTest;
import io.eventuate.tram.examples.basic.events.AbstractTramEventTestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BrokerEventTest.Config.class)
public class BrokerEventTest extends AbstractTramEventTest {

    @Configuration
    @EnableAutoConfiguration
    @Import({AbstractTramEventTestConfiguration.class})
    public static class Config {
    }
}
