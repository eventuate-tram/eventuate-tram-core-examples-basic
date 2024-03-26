package io.eventuate.tram.examples.basic.events.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "event")
public class EventConfigurationProperties {

    private String aggregateSuffix = "";

    public String getAggregateSuffix() {
        return aggregateSuffix;
    }

    public void setAggregateSuffix(String aggregateSuffix) {
        this.aggregateSuffix = aggregateSuffix;
    }
}
