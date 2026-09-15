package com.anurastores.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.anurastores.model.Employee;
import com.anurastores.util.DBConnection;

public class EmployeeDAO {

    // 1. ADD EMPLOYEE
    public boolean addEmployee(Employee employee) {

        String sql = "INSERT INTO employee "
                + "(first_name, last_name, nic, phone, email, address, "
                + "position, hire_date, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getNic());
            statement.setString(4, employee.getPhone());
            statement.setString(5, employee.getEmail());
            statement.setString(6, employee.getAddress());
            statement.setString(7, employee.getPosition());
            statement.setDate(8, employee.getHireDate());
            statement.setString(9, employee.getStatus());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // 2. VIEW ALL EMPLOYEES
    public List<Employee> getAllEmployees() {

        List<Employee> employees = new ArrayList<Employee>();

        String sql = "SELECT employee_id, first_name, last_name, nic, "
                + "phone, email, address, position, hire_date, status "
                + "FROM employee "
                + "ORDER BY employee_id DESC";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery()
        ) {

            while (result.next()) {

                Employee employee = new Employee(
                        result.getInt("employee_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("nic"),
                        result.getString("phone"),
                        result.getString("email"),
                        result.getString("address"),
                        result.getString("position"),
                        result.getDate("hire_date"),
                        result.getString("status")
                );

                employees.add(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }


    // 3. SEARCH EMPLOYEE
    public List<Employee> searchEmployees(String keyword) {

        List<Employee> employees = new ArrayList<Employee>();

        String sql = "SELECT employee_id, first_name, last_name, nic, "
                + "phone, email, address, position, hire_date, status "
                + "FROM employee "
                + "WHERE CAST(employee_id AS CHAR) LIKE ? "
                + "OR first_name LIKE ? "
                + "OR last_name LIKE ? "
                + "OR CONCAT(first_name, ' ', last_name) LIKE ? "
                + "OR nic LIKE ? "
                + "OR position LIKE ? "
                + "OR status LIKE ? "
                + "ORDER BY employee_id DESC";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            String searchValue = "%" + keyword + "%";

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);
            statement.setString(3, searchValue);
            statement.setString(4, searchValue);
            statement.setString(5, searchValue);
            statement.setString(6, searchValue);
            statement.setString(7, searchValue);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Employee employee = new Employee(
                            result.getInt("employee_id"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getString("nic"),
                            result.getString("phone"),
                            result.getString("email"),
                            result.getString("address"),
                            result.getString("position"),
                            result.getDate("hire_date"),
                            result.getString("status")
                    );

                    employees.add(employee);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }


    // 4. UPDATE EMPLOYEE
    public boolean updateEmployee(Employee employee) {

        String sql = "UPDATE employee SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "nic = ?, "
                + "phone = ?, "
                + "email = ?, "
                + "address = ?, "
                + "position = ?, "
                + "hire_date = ?, "
                + "status = ? "
                + "WHERE employee_id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getNic());
            statement.setString(4, employee.getPhone());
            statement.setString(5, employee.getEmail());
            statement.setString(6, employee.getAddress());
            statement.setString(7, employee.getPosition());
            statement.setDate(8, employee.getHireDate());
            statement.setString(9, employee.getStatus());
            statement.setInt(10, employee.getEmployeeId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // 5. DEACTIVATE EMPLOYEE
    public boolean deactivateEmployee(int employeeId) {

        String sql = "UPDATE employee "
                + "SET status = 'INACTIVE' "
                + "WHERE employee_id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, employeeId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}