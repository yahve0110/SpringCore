package com.yahve.command;
import com.yahve.service.AccountService;
import com.yahve.service.impl.AccountServiceImpl;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class WithdrawCommand implements OperationCommand {
    private final AccountServiceImpl accountServiceImpl;

    public WithdrawCommand(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        int accountId = -1;
        double amount = -1;

        while (accountId < 0) {
            System.out.print("Enter account ID for withdrawal: ");
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
            System.out.print("Enter amount to withdraw: ");
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Withdrawal amount must be greater than zero.");
                    amount = -1;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid amount (a number).");
                scanner.next();
            }
        }

        try {
            accountServiceImpl.withdraw(accountId, amount);
            System.out.println("Amount " + amount + " withdrawn from account ID: " + accountId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.WITHDRAW;
    }
}
