package io.eventuate.tram.examples.basic.commands.producer;

import io.eventuate.tram.examples.basic.commands.common.CommandConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties(CommandConfigurationProperties.class)
public class CommandProducerConfiguration {


}
