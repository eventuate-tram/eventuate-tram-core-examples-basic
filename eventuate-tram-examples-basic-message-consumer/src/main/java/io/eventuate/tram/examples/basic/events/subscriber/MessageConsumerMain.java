package io.eventuate.tram.examples.basic.events.subscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageConsumerMain {

    public static void main(String[] args) {
        SpringApplication.run(MessageConsumerMain.class, args);
    }
}
