package com.yahve.listener;

import com.yahve.command.ConsoleOperationType;
import com.yahve.command.OperationCommand;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class OperationsConsoleListener implements Runnable {
    private final Map<ConsoleOperationType, OperationCommand> commandMap = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public OperationsConsoleListener(List<OperationCommand> commands) {
        commands.forEach(command -> commandMap.put(command.getOperationType(), command));
    }

    @Override
    public void run() {
        System.out.println("Welcome to the Banking Application!");

        while (true) {
            System.out.println("\nPlease enter one of the operation types:");
            for (ConsoleOperationType operation : commandMap.keySet()) {
                System.out.println("- " + operation);
            }

            System.out.print("Enter operation: ");
            String userInput = scanner.nextLine().trim().toUpperCase();

            try {
                ConsoleOperationType operationType = ConsoleOperationType.valueOf(userInput);
                executeCommand(operationType);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command: '" + userInput + "'. Please enter a valid operation.");
            }
        }
    }


    public void executeCommand(ConsoleOperationType operationType) {
        OperationCommand command = commandMap.get(operationType);
        if (command != null) {
            try {
                command.execute();
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        } else {
            System.out.println("Command not found for operation: " + operationType);
        }
    }
}

