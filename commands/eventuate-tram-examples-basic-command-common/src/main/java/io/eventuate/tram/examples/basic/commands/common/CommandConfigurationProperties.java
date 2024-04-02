package io.eventuate.tram.examples.basic.commands.common;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "command")
public class CommandConfigurationProperties {

    private String commandChannel = "commandChannel";
    private String replyChannel = "replyChannel";

    public String getCommandChannel() {
        return commandChannel;
    }

    public void setCommandChannel(String commandChannel) {
        this.commandChannel = commandChannel;
    }

    public String getReplyChannel() {
        return replyChannel;
    }

    public void setReplyChannel(String replyChannel) {
        this.replyChannel = replyChannel;
    }
}
