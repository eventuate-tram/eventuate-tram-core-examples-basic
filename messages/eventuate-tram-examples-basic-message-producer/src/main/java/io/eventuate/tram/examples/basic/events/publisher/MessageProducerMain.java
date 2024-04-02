package io.eventuate.tram.examples.basic.events.publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MessageProducerConfiguration.class)
public class MessageProducerMain {

    public static void main(String[] args) {
        SpringApplication.run(MessageProducerMain.class, args);
    }
}
