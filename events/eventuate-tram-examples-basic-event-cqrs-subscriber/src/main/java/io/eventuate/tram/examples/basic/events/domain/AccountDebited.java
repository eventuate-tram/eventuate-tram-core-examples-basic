package io.eventuate.tram.examples.basic.events.domain;

import io.eventuate.tram.events.common.DomainEvent;

public record AccountDebited(long amount) implements DomainEvent {}
