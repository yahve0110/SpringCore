package com.yahve.command;

import com.yahve.service.AccountService;
import com.yahve.service.impl.AccountServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CloseAccountCommand implements OperationCommand {
    private final AccountServiceImpl accountServiceImpl;

    public CloseAccountCommand(AccountService accountService, AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
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
