package com.yahve.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    public Account(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Account() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "money_amount", nullable = false)
    private BigDecimal moneyAmount;

    // Getters
    public int getId() {
        return id;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
