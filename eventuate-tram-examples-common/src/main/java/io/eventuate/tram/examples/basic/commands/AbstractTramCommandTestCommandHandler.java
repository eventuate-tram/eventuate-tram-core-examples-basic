package io.eventuate.tram.examples.basic.commands;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.commands.consumer.CommandHandlerMethod;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.commands.consumer.PathVariable;

public class AbstractTramCommandTestCommandHandler {

  @CommandHandlerMethod(path="/customers/{customerId}", replyChannel = "'CustomerAggregate'", partitionId="path['customerId']")
  public Success doSomething(@PathVariable("customerId") String customerId, CommandMessage<DoSomethingCommand> cm) {
    System.out.println("customerId=" + customerId);
    System.out.println("cm=" + cm);
    return new Success();

  }

}
