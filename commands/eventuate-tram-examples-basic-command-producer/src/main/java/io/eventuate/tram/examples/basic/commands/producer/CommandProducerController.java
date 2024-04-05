package io.eventuate.tram.examples.basic.commands.producer;

import io.eventuate.tram.messaging.common.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommandProducerController {

    @Autowired
    private CommandProducingService commandProducingService;

    @PostMapping("/send")
    public ProduceResponse publish(@RequestBody ProduceRequest produceRequest) {
        return new ProduceResponse(commandProducingService.produceCommand(produceRequest));
    }

    @GetMapping("/replies")
    public ResponseEntity<String> getReplies() {
        List<Message> replies = commandProducingService.getReplies();
        if (replies.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }

}
