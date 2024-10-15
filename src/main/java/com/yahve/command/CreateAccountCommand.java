package com.yahve.command;

import com.yahve.service.impl.AccountServiceImpl;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class CreateAccountCommand implements OperationCommand {
    private final AccountServiceImpl accountService;

    public CreateAccountCommand(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID to create account: ");
        boolean validInput = false;
        int userId = scanner.nextInt();
        while (!validInput) {
            try {
                System.out.print("Enter user ID to create account (must be a positive number): ");
                userId = scanner.nextInt();

                if (userId > 0) {
                    validInput = true;
                } else {
                    System.out.println("Error: User ID must be a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a valid positive number.");
                scanner.next();
            }
        }

        accountService.createAccount(userId);
        System.out.println("Account created for user ID: " + userId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
