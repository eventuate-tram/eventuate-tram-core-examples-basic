package io.eventuate.tram.examples.basic.commands;

import io.eventuate.tram.commands.common.Command;

public class ReserveCreditCommand implements Command {

  private String customerId;

  public ReserveCreditCommand() {
  }

  public ReserveCreditCommand(String customerId) {
    this.customerId = customerId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }
}
