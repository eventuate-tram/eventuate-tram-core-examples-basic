package io.eventuate.tram.examples.basic.events;

public interface AggregateSupplier {
  String getAggregateType();

  String getAggregateId();
}
