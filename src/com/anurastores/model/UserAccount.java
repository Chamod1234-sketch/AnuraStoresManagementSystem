package com.anurastores.model;

public class UserAccount {

    private int userId;
    private int employeeId;
    private String employeeName;
    private String username;
    private String role;
    private String status;


    public UserAccount() {
    }


    public UserAccount(
            int userId,
            int employeeId,
            String employeeName,
            String username,
            String role,
            String status) {

        this.userId = userId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.username = username;
        this.role = role;
        this.status = status;
    }


    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getEmployeeId() {
        return employeeId;
    }


    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }


    public String getEmployeeName() {
        return employeeName;
    }


    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
}