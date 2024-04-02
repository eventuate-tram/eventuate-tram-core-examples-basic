package io.eventuate.tram.examples.basic.commands.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CommandConsumerConfiguration.class)
public class CommandConsumerMain {

    public static void main(String[] args) {
        SpringApplication.run(CommandConsumerMain.class, args);
    }
}
