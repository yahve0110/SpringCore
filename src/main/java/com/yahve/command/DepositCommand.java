package com.yahve.command;

import com.yahve.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class DepositCommand implements OperationCommand {
    private final AccountService accountService;

    public DepositCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        int accountId = -1;
        BigDecimal amount = BigDecimal.valueOf(-1);

        while (accountId < 0) {
            System.out.print("Enter account ID for deposit: ");
            if (scanner.hasNextInt()) {
                accountId = scanner.nextInt();
                if (accountId < 0) {
                    System.out.println("Account ID cannot be negative. Please enter a valid account ID.");
                    accountId = -1;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid account ID (a number).");
                scanner.next();
            }
        }

        while (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.print("Enter amount to deposit: ");
            if (scanner.hasNextBigDecimal()) {
                amount = scanner.nextBigDecimal();
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Deposit amount must be greater than zero.");
                    amount = BigDecimal.valueOf(-1);
                }
            } else {
                System.out.println("Invalid input. Please enter a valid amount (a number).");
                scanner.next();
            }
        }

        try {
            accountService.deposit(accountId, amount);
            System.out.println("Successfully deposited " + amount + " to account ID " + accountId);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.DEPOSIT;
    }
}
