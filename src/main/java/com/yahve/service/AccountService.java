package com.yahve.service;

import com.yahve.entity.Account;
import com.yahve.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    Map<Integer, Account> accountMap = new HashMap<>();
    private final UserService userService;

    private int generatedAccountId = 1;

    @Value("${account.default-amount}")
    private BigDecimal defaultAmount;

    @Value("${account.transfer-commission}")
    private BigDecimal transferCommission;


    public AccountService(UserService userService) {
        this.userService = userService;
    }

    public Account createAccount(int userId) {
        User user = userService.findUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }

        Account account = new Account(generatedAccountId, userId, defaultAmount);
        accountMap.put(generatedAccountId, account);
        generatedAccountId++;
        user.addAccount(account);
        return account;
    }


    public void closeAccount(int accountId) {
        Account accountToClose = accountMap.get(accountId);
        if (accountToClose == null) {
            throw new IllegalArgumentException("Account with ID " + accountId + " not found.");
        }
        User user = userService.findUserById(accountToClose.getUserId());
        if (user.getAccounts().size() == 1) {
            throw new IllegalStateException("Cannot close the only account of the user.");
        }

        Account targetAccount = user.getAccounts()
                .stream()
                .filter(account -> account.getId() != accountId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No valid target account found for transfer."));

        targetAccount.deposit(accountToClose.getMoneyAmount());
        user.getAccounts().remove(accountToClose);
        accountMap.remove(accountId);
        System.out.println("Account with ID " + accountId + " has been closed and funds transferred to account ID " + targetAccount.getId());
    }



    public void deposit(int accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }

        Account account = accountMap.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account with ID " + accountId + " not found.");
        }

        account.deposit(amount);
        System.out.println("Amount " + amount + " deposited to account ID " + accountId);
    }


    public void withdraw(int accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }

        Account account = accountMap.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account with ID " + accountId + " not found.");
        }

        if (account.getMoneyAmount().compareTo(amount) < 0) {
            throw new IllegalStateException("Not enough funds in account ID " + accountId + ". Available: " + account.getMoneyAmount());
        }

        account.withdraw(amount);
        System.out.println("Amount " + amount + " withdrawn from account ID " + accountId);
    }


    public void transfer(int fromAccountId, int toAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }

        Account fromAccount = accountMap.get(fromAccountId);
        Account toAccount = accountMap.get(toAccountId);

        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("One or both accounts not found.");
        }

        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        User fromUser = userService.findUserById(fromAccount.getUserId());
        User toUser = userService.findUserById(toAccount.getUserId());

        BigDecimal finalAmount = amount;

        if (!fromUser.equals(toUser)) {
            BigDecimal commission = amount.multiply(transferCommission).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            finalAmount = amount.subtract(commission);
            System.out.println("Transfer commission applied: " + commission);
        }

        if (fromAccount.getMoneyAmount().compareTo(amount) < 0) {
            throw new IllegalStateException("Not enough funds in account ID " + fromAccountId + ". Available: " + fromAccount.getMoneyAmount());
        }
        fromAccount.withdraw(amount);
        toAccount.deposit(finalAmount);

        System.out.println("Amount " + amount + " transferred from account ID " + fromAccountId + " to account ID " + toAccountId);
    }
}
