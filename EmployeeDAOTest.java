package com.anurastores.dao;

import java.util.List;

import com.anurastores.model.Employee;

public class EmployeeDAOTest {

    public static void main(String[] args) {

        EmployeeDAO employeeDAO = new EmployeeDAO();

        List<Employee> employees = employeeDAO.getAllEmployees();

        for (Employee employee : employees) {

            System.out.println(
                    employee.getEmployeeId() + " | "
                    + employee.getFirstName() + " "
                    + employee.getLastName() + " | "
                    + employee.getPosition() + " | "
                    + employee.getStatus()
            );
        }
    }
}