package com.yahve.command;

import com.yahve.entity.User;
import com.yahve.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUserCommand implements OperationCommand {
    private final UserService userService;

    public CreateUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter login: ");
        String login = scanner.nextLine();
        if (login == null || login.isEmpty()) {
            System.out.println("Login cannot be is empty");
            return;
        }
        User user = userService.createUser(login);
        System.out.println("User created: " +  user.toString());

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
