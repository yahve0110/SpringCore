package com.yahve.service;

import com.yahve.entity.Account;
import com.yahve.entity.User;
import com.yahve.helper.TransactionalHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AccountService {

    private final TransactionalHelper transactionalHelper;

    @Value("${account.default-amount}")
    private BigDecimal defaultAmount;

    @Value("${account.transfer-commission}")
    private BigDecimal transferCommission;

    public AccountService( TransactionalHelper transactionalHelper) {
        this.transactionalHelper = transactionalHelper;
    }

    public Account createAccount(int userId) {
        return transactionalHelper.executeInTransaction(session -> {
            User user = session.get(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found for ID: " + userId);
            }

            Account account = new Account(defaultAmount);
            account.setUser(user);
            session.persist(account);
            user.getAccounts().add(account);
            return account;
        });
    }

    public void deposit(int accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }

        transactionalHelper.executeInTransaction(session -> {
            Account account = session.get(Account.class, accountId);
            if (account == null) {
                throw new IllegalArgumentException("Account with ID " + accountId + " not found.");
            }

            account.deposit(amount);
            session.merge(account);
        });
    }

    public void withdraw(int accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }

        transactionalHelper.executeInTransaction(session -> {
            Account account = session.get(Account.class, accountId);
            if (account == null) {
                throw new IllegalArgumentException("Account with ID " + accountId + " not found.");
            }

            if (account.getMoneyAmount().compareTo(amount) < 0) {
                throw new IllegalStateException("Not enough funds in account ID " + accountId + ". Available: " + account.getMoneyAmount());
            }

            account.withdraw(amount);
            session.merge(account);
        });

    }

    public void closeAccount(int accountId) {
        transactionalHelper.executeInTransaction(session -> {
            Account accountToClose = session.get(Account.class, accountId);
            if (accountToClose == null) {
                throw new IllegalArgumentException("Account with ID " + accountId + " not found.");
            }

            User user = accountToClose.getUser();
            if (user.getAccounts().size() == 1) {
                throw new IllegalStateException("Cannot close the only account of the user.");
            }

            Account targetAccount = user.getAccounts().stream()
                    .filter(account -> account.getId() != accountId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No valid target account found for transfer."));

            targetAccount.deposit(accountToClose.getMoneyAmount());
            user.getAccounts().remove(accountToClose);
            session.remove(accountToClose);
            session.merge(targetAccount);
            session.merge(user);
        });
    }

    public void transfer(int fromAccountId, int toAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }

        transactionalHelper.executeInTransaction(session -> {
            Account fromAccount = session.get(Account.class, fromAccountId);
            Account toAccount = session.get(Account.class, toAccountId);

            if (fromAccount == null || toAccount == null) {
                throw new IllegalArgumentException("One or both accounts not found.");
            }

            if (fromAccount.equals(toAccount)) {
                throw new IllegalArgumentException("Cannot transfer to the same account.");
            }

            User fromUser = fromAccount.getUser();
            User toUser = toAccount.getUser();
            BigDecimal finalAmount = amount;

            if (!fromUser.equals(toUser)) {
                BigDecimal commission = amount.multiply(transferCommission).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
                finalAmount = amount.subtract(commission);
            }

            if (fromAccount.getMoneyAmount().compareTo(amount) < 0) {
                throw new IllegalStateException("Not enough funds in account ID " + fromAccountId + ". Available: " + fromAccount.getMoneyAmount());
            }

            fromAccount.withdraw(amount);
            toAccount.deposit(finalAmount);
            session.merge(fromAccount);
            session.merge(toAccount);
        });
    }


}

