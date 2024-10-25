package com.yahve.service;

import com.yahve.entity.User;
import com.yahve.helper.TransactionalHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final SessionFactory sessionFactory;
    private final TransactionalHelper transactionHelper;

    public UserService(SessionFactory sessionFactory,
                       TransactionalHelper transactionHelper) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public User createUser(String login) {
        try (Session session = sessionFactory.openSession()) {
            User existingUser = session
                    .createQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResult();

            if (existingUser != null) {
                throw new IllegalArgumentException("User already exists with login: " + login);
            }
        }

        User user = new User(login);
        return transactionHelper.executeInTransaction(session -> {
            session.persist(user);
            return user;
        });
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("SELECT u FROM User u", User.class)
                    .list();
        }
    }
}
