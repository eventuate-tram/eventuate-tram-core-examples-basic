package io.eventuate.tram.examples.basic.commands;

import io.eventuate.tram.commands.consumer.*;
import io.eventuate.tram.messaging.common.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class TramCommandTestCommandHandler {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private String commandChannel;

  public TramCommandTestCommandHandler(String commandChannel) {
   this.commandChannel = commandChannel;
  }

  public Message doSomething(CommandMessage<DoSomethingCommand> cm, PathVariables pvs) {
    logger.info("cm=" + cm);
    return withSuccess();

  }

  public CommandHandlers getCommandHandlers() {
    return CommandHandlersBuilder
            .fromChannel(commandChannel)
            .onMessage(DoSomethingCommand.class, this::doSomething)
            .build();

  }
}
