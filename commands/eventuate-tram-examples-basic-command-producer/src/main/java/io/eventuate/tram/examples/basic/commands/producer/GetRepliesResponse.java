package io.eventuate.tram.examples.basic.commands.producer;

import io.eventuate.tram.messaging.common.Message;

import java.util.List;

public class GetRepliesResponse {
    private final List<Message> replies;

    public GetRepliesResponse(List<Message> replies) {
        this.replies = replies;
    }

    public List<Message> getReplies() {
        return replies;
    }
}
