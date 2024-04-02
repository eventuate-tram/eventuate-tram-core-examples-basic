package io.eventuate.tram.examples.basic.commands;

import io.eventuate.tram.examples.basic.commands.producer.CommandProducerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CommandProducerConfiguration.class})
public class CommandProducerMain {

    public static void main(String[] args) {
        SpringApplication.run(CommandProducerMain.class, args);
    }
}
