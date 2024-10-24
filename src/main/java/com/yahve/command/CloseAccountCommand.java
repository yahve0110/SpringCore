package com.yahve.command;

import com.yahve.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CloseAccountCommand implements OperationCommand {
    private final AccountService accountServiceImpl;

    public CloseAccountCommand(AccountService accountService) {
        this.accountServiceImpl = accountService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account ID to close: ");
        int accountId = scanner.nextInt();
        accountServiceImpl.closeAccount(accountId);
        System.out.println("Account ID " + accountId + " has been closed.");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
