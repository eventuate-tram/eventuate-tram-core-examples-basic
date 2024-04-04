package io.eventuate.tram.examples.basic.commands.producer;

import io.eventuate.tram.examples.basic.commands.common.CommandConfigurationProperties;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties(CommandConfigurationProperties.class)
public class CommandProducerConfiguration {

    @Bean
    public CommandReplyHandler commandReplyHandler(CommandConfigurationProperties commandConfigurationProperties, MessageConsumer messageConsumer) {
        return new CommandReplyHandler(messageConsumer, commandConfigurationProperties);
    }
}
