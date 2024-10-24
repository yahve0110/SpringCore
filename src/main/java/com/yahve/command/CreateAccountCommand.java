package com.yahve.command;

import com.yahve.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class CreateAccountCommand implements OperationCommand {
    private final AccountService accountService;

    public CreateAccountCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        int userId = 0;

        // Loop to get valid input
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
                scanner.next();  // Clear the invalid input
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
