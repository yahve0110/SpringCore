package com.yahve.service;

import com.yahve.entity.Account;

public interface AccountService {
    Account createAccount(int userId);
    void closeAccount(int accountId);
    void deposit(int accountId, double amount);
    void withdraw(int accountId, double amount);
    void transfer(int fromAccountId, int toAccountId, double amount);
}
