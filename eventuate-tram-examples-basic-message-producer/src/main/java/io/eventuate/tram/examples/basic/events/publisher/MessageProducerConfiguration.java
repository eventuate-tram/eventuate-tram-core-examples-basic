package io.eventuate.tram.examples.basic.events.publisher;

import io.eventuate.tram.examples.basic.commands.common.MessageConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties(MessageConfigurationProperties.class)
public class MessageProducerConfiguration {


}
