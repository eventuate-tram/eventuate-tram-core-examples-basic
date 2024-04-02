package io.eventuate.tram.examples.basic.commands.common;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "message")
public class MessageConfigurationProperties {

    private String channel = "someChannel";

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}
