package io.eventuate.tram.examples.basic.events.publisher;

public record PublishRequest(long accountId, long amount) {
}
