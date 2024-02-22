package io.eventuate.tram.examples.basic.commands.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandProducerController {

    @Autowired
    private CommandProducingService commandProducingService;

    @PostMapping("/send")
    public ProduceResponse publish(@RequestBody ProduceRequest produceRequest) {
        return new ProduceResponse(commandProducingService.produceCommand(produceRequest));
    }

}
