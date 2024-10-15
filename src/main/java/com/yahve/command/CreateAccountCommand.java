package com.yahve.command;

import com.yahve.service.impl.AccountServiceImpl;
import org.springframework.stereotype.Component;

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
        int userId = scanner.nextInt();
        accountService.createAccount(userId);
        System.out.println("Account created for user ID: " + userId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
