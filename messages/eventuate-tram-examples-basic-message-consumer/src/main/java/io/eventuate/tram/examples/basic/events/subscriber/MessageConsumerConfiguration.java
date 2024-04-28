package io.eventuate.tram.examples.basic.events.subscriber;

import io.eventuate.tram.examples.basic.commands.common.MessageConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MessageConfigurationProperties.class)
@ComponentScan
public class MessageConsumerConfiguration {

}
