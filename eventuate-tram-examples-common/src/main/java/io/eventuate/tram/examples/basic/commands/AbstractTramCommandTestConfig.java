package io.eventuate.tram.examples.basic.commands;

public class AbstractTramCommandTestConfig {
  private long uniqueId = System.currentTimeMillis();

  private String resource = "/customers/3343";
  private String commandChannel = "commandChannel" + uniqueId;
  private String commandDispatcheId = "commandDispatcheId" + uniqueId;
  private String customerChannel = "customerChannel" + uniqueId;

  public String getResource() {
    return resource;
  }

  public String getCommandChannel() {
    return commandChannel;
  }

  public String getCommandDispatcheId() {
    return commandDispatcheId;
  }


  public String getCustomerChannel() {
    return customerChannel;
  }

  public long getUniqueId() {
    return uniqueId;
  }
}
