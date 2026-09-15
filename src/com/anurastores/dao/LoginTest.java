package com.anurastores.dao;

import com.anurastores.model.User;

public class LoginTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        User user = userDAO.authenticate("admin", "admin123");

        if (user != null) {
            System.out.println("Login successful!");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Role: " + user.getRole());
        } else {
            System.out.println("Invalid username or password!");
        }
    }
}