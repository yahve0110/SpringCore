package com.yahve.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final int userId;
    private final String login;
    private final List<Account> accounts = new ArrayList<>();

    public User(int userId, String login) {
        this.userId = userId;
        this.login = login;
    }

    //Getters
    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{")
                .append("userId=").append(userId)
                .append(", login='").append(login).append('\'')
                .append(", accounts=").append(accounts)
                .append('}');
        return sb.toString();
    }
}
