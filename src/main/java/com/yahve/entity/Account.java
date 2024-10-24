package com.yahve.entity;

import java.math.BigDecimal;

public class Account {

    private final int id;
    private final int userId;
    private BigDecimal moneyAmount;

    public Account(int id, int userId, BigDecimal moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    // Getters
    public int getId() {
        return id;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        moneyAmount = moneyAmount.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (moneyAmount.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds. Cannot withdraw " + amount);
        }
        moneyAmount = moneyAmount.subtract(amount);
    }
}
