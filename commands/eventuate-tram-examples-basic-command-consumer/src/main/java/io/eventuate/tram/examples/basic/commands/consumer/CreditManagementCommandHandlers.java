package io.eventuate.tram.examples.basic.commands.consumer;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandHandlersBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.examples.basic.commands.common.ReserveCreditCommand;
import io.eventuate.tram.messaging.common.Message;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class CreditManagementCommandHandlers {

  private final String commandChannel;

  public CreditManagementCommandHandlers(String commandChannel) {
    this.commandChannel = commandChannel;
  }


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


}
