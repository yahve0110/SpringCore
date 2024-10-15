package com.yahve.command;

import com.yahve.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TransferCommand implements OperationCommand {
    private final AccountService accountService;

    public TransferCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        int fromAccountId = -1;
        int toAccountId = -1;
        double amount = -1;

        while (fromAccountId < 0) {
            System.out.print("Enter from account ID: ");
            if (scanner.hasNextInt()) {
                fromAccountId = scanner.nextInt();
                if (fromAccountId < 0) {
                    System.out.println("Account ID cannot be negative. Please enter a valid account ID.");
                    fromAccountId = -1;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid account ID (a number).");
                scanner.next();
            }
        }

        while (toAccountId < 0) {
            System.out.print("Enter to account ID: ");
            if (scanner.hasNextInt()) {
                toAccountId = scanner.nextInt();
                if (toAccountId < 0) {
                    System.out.println("Account ID cannot be negative. Please enter a valid account ID.");
                    toAccountId = -1;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid account ID (a number).");
                scanner.next();
            }
        }

        while (amount <= 0) {
            System.out.print("Enter amount to transfer: ");
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Transfer amount must be greater than zero.");
                    amount = -1;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid amount (a number).");
                scanner.next();
            }
        }

        try {
            accountService.transfer(fromAccountId, toAccountId, amount);
            System.out.println("Transferred " + amount + " from account ID " + fromAccountId + " to account ID " + toAccountId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.TRANSFER;
    }
}
