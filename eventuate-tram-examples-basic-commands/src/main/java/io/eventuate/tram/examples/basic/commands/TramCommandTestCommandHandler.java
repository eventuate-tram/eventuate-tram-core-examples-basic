package io.eventuate.tram.examples.basic.commands;

import io.eventuate.tram.commands.consumer.*;
import io.eventuate.tram.messaging.common.Message;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class TramCommandTestCommandHandler {

  public CommandHandlers getCommandHandlers() {
    return CommandHandlersBuilder
            .fromChannel(commandChannel)
            .onMessage(ReserveCreditCommand.class, this::reserveCredit)
            .build();

  }
  public Message reserveCredit(CommandMessage<ReserveCreditCommand> cm) {

    System.out.println("customerId=" + cm.getCommand().getCustomerId());
    System.out.println("cm=" + cm);
    return withSuccess();

  }

  private String commandChannel;

  public TramCommandTestCommandHandler(String commandChannel) {
    this.commandChannel = commandChannel;
  }


}
