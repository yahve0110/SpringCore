package com.yahve.command;

import com.yahve.entity.Account;
import com.yahve.entity.User;
import com.yahve.service.impl.UserServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListUsersCommand implements OperationCommand{
    private final UserServiceImpl userService;

    public ListUsersCommand(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("User list is empty!");
        } else {
            System.out.println("Users and their accounts:");
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                System.out.println((i + 1) + ". User ID: " + user.getUserId() + ", Login: " + user.getLogin());

                List<Account> accounts = user.getAccounts();
                if (accounts.isEmpty()) {
                    System.out.println("   No accounts found for this user.");
                } else {
                    for (int j = 0; j < accounts.size(); j++) {
                        Account account = accounts.get(j);
                        System.out.println("   " + (j + 1) + ". Account ID: " + account.getId() +
                                ", Balance: $" + String.format("%.2f", account.getMoneyAmount()));
                    }
                }
                System.out.println();
            }
        }
    }



    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}
