package io.eventuate.tram.examples.basic.events.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CqrsEventsSubscriberController {

    @Autowired
    private AccountEventsConsumer accountEventsConsumer;

    @GetMapping("/events")
    public ResponseEntity<String> getEvents(@RequestParam("accountId") String accountId) {
        var events = accountEventsConsumer.getEventsFor(accountId);
        if (events.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }
}
