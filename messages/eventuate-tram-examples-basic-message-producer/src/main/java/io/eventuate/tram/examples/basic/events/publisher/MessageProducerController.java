package io.eventuate.tram.examples.basic.events.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageProducerController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MessageProducingService messageProducingService;

    public MessageProducerController(MessageProducingService messageProducingService) {
        this.messageProducingService = messageProducingService;
    }

    @PostMapping("/produce")
    public void produce(@RequestBody ProduceRequest produceRequest) {
        logger.info("Producing {}", produceRequest);
        messageProducingService.sendMessage(produceRequest.accountId());
        logger.info("produced {}", produceRequest);
    }

}
