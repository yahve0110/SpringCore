package com.yahve.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User {

    public User(){
    }

    public User( String login) {
        this.login = login;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"user_id\"")
    private int userId;

    @Column(name = "login", unique = true, nullable = false)
    private  String login;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private  List<Account> accounts = new ArrayList<>();

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
