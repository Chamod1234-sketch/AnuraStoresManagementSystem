package com.anurastores.model;

import java.sql.Date;

public class Employee {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String nic;
    private String phone;
    private String email;
    private String address;
    private String position;
    private Date hireDate;
    private String status;

    public Employee() {
    }

    public Employee(int employeeId, String firstName, String lastName,
                    String nic, String phone, String email,
                    String address, String position,
                    Date hireDate, String status) {

        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.position = position;
        this.hireDate = hireDate;
        this.status = status;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}