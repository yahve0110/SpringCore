package com.yahve.entity;

public class Account {

    private final int id;
    private final int userId;
    private double moneyAmount;

    public Account(int id, int userId, double moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    //Getters
    public int getId() {
        return id;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        moneyAmount += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (moneyAmount - amount < 0) {
            throw new IllegalArgumentException("Insufficient funds. Cannot withdraw " + amount);
        } else {
            moneyAmount -= amount;
        }
    }
}
