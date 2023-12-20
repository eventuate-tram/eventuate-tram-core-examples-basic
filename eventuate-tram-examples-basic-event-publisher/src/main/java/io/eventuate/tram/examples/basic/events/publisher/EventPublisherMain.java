package io.eventuate.tram.examples.basic.events.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(EventPublisherConfiguration.class)
public class EventPublisherMain {

    public static void main(String[] args) {
        SpringApplication.run(EventPublisherMain.class, args);
    }
}
