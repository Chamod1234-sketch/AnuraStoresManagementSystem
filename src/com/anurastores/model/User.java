package com.anurastores.model;

public class User {

    private int userId;
    private int employeeId;
    private String username;
    private String role;

    public User(int userId, int employeeId, String username, String role) {
        this.userId = userId;
        this.employeeId = employeeId;
        this.username = username;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}