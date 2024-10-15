package com.yahve.command;
import com.yahve.service.impl.AccountServiceImpl;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class DepositCommand implements OperationCommand {
    private final AccountServiceImpl accountServiceImpl;

    public DepositCommand(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        int accountId = -1;
        double amount = -1;

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

        while (amount <= 0) {
            System.out.print("Enter amount to deposit: ");
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Deposit amount must be greater than zero.");
                    amount = -1;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid amount (a number).");
                scanner.next();
            }
        }

        try {
            accountServiceImpl.deposit(accountId, amount);
            System.out.println("Successfully deposited " + amount + " to account ID " + accountId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.DEPOSIT;
    }
}
