package io.eventuate.tram.examples.basic.events.publisher;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.examples.basic.events.common.EventConfigurationProperties;
import io.eventuate.tram.examples.basic.events.domain.AccountDebited;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class EventPublisherController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DomainEventPublisher domainEventPublisher;
    private final EventConfigurationProperties eventConfigurationProperties;

    @Autowired
    public EventPublisherController(DomainEventPublisher domainEventPublisher, EventConfigurationProperties eventConfigurationProperties) {
        this.domainEventPublisher = domainEventPublisher;
        this.eventConfigurationProperties = eventConfigurationProperties;
    }

    @PostMapping("/publish")
    public void publish(@RequestBody PublishRequest publishRequest) {
        String aggregateType = "Account" + eventConfigurationProperties.getAggregateSuffix();
        logger.info("Publishing {} to {}", publishRequest, aggregateType);
        domainEventPublisher.publish(aggregateType, publishRequest.accountId(), Collections.singletonList(new AccountDebited(publishRequest.amount())));
        logger.info("Published {} to {}", publishRequest, aggregateType);
    }

}
