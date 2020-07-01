package io.eventuate.tram.examples.basic.events;

import io.eventuate.tram.examples.basic.events.domain.Account;

public class AbstractTramEventTestConfig implements IdSupplier, AggregateSupplier {

  private long uniqueId = System.currentTimeMillis();
  private String  aggregateType = Account.class.getName() + uniqueId;
  private String aggregateId = "accountId" + uniqueId;

  @Override
  public Long get() {
    return uniqueId;
  }

  @Override
  public String getAggregateType() {
    return aggregateType;
  }

  @Override
  public String getAggregateId() {
    return aggregateId;
  }
}
