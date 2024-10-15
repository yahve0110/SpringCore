package com.yahve.command;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getOperationType();
}
