package io.eventuate.tram.examples.basic.events.publisher;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.examples.basic.events.domain.AccountDebited;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class PublisherController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private DomainEventPublisher domainEventPublisher;

    @Autowired
    public PublisherController(DomainEventPublisher domainEventPublisher) {
        this.domainEventPublisher = domainEventPublisher;
    }

    @PostMapping("/publish")
    public void publish(@RequestBody PublishRequest publishRequest) {
        logger.info("Publishing {}", publishRequest);
        domainEventPublisher.publish("Account", publishRequest.accountId(), Collections.singletonList(new AccountDebited(publishRequest.amount())));
        logger.info("Published {}", publishRequest);
    }

}
