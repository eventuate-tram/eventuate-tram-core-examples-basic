package io.eventuate.tram.examples.basic.commands.common;

import io.eventuate.tram.commands.common.Command;

public record ReserveCreditCommand(String customerId) implements Command {
}
