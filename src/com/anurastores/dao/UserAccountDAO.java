package com.anurastores.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.anurastores.model.UserAccount;
import com.anurastores.util.DBConnection;

public class UserAccountDAO {

    // =====================================================
    // 1. ADD USER ACCOUNT
    // =====================================================
    public boolean addUserAccount(
            int employeeId,
            String username,
            String password,
            String role) {

        if (employeeId <= 0
                || username == null
                || username.trim().isEmpty()
                || password == null
                || password.trim().isEmpty()
                || !isValidRole(role)) {

            return false;
        }

        // Only active employees can receive a new account
        if (!isEmployeeActive(employeeId)) {
            return false;
        }

        // One username must be unique
        if (usernameExists(username, 0)) {
            return false;
        }

        // One employee -> one user account
        if (employeeHasAccount(employeeId, 0)) {
            return false;
        }

        String sql =
                "INSERT INTO user_account "
                + "(employee_id, username, password_hash, role, status) "
                + "VALUES (?, ?, SHA2(?, 256), ?, 'ACTIVE')";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    employeeId);

            statement.setString(
                    2,
                    username.trim());

            statement.setString(
                    3,
                    password);

            statement.setString(
                    4,
                    role.toUpperCase());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // =====================================================
    // 2. VIEW ALL USER ACCOUNTS
    // =====================================================
    public List<UserAccount> getAllUserAccounts() {

        List<UserAccount> accounts =
                new ArrayList<UserAccount>();

        String sql =
                "SELECT u.user_id, "
                + "u.employee_id, "
                + "CONCAT(e.first_name, ' ', e.last_name) "
                + "AS employee_name, "
                + "u.username, "
                + "u.role, "
                + "u.status "
                + "FROM user_account u "
                + "LEFT JOIN employee e "
                + "ON u.employee_id = e.employee_id "
                + "ORDER BY u.user_id";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result =
                    statement.executeQuery()
        ) {

            while (result.next()) {

                accounts.add(
                        createUserAccountFromResult(
                                result));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return accounts;
    }


    // =====================================================
    // 3. SEARCH USER ACCOUNTS
    // ID / username / employee / role / status
    // =====================================================
    public List<UserAccount> searchUserAccounts(
            String keyword) {

        List<UserAccount> accounts =
                new ArrayList<UserAccount>();

        String sql =
                "SELECT u.user_id, "
                + "u.employee_id, "
                + "CONCAT(e.first_name, ' ', e.last_name) "
                + "AS employee_name, "
                + "u.username, "
                + "u.role, "
                + "u.status "
                + "FROM user_account u "
                + "LEFT JOIN employee e "
                + "ON u.employee_id = e.employee_id "
                + "WHERE CAST(u.user_id AS CHAR) LIKE ? "
                + "OR CAST(u.employee_id AS CHAR) LIKE ? "
                + "OR u.username LIKE ? "
                + "OR e.first_name LIKE ? "
                + "OR e.last_name LIKE ? "
                + "OR CONCAT(e.first_name, ' ', e.last_name) LIKE ? "
                + "OR u.role LIKE ? "
                + "OR u.status LIKE ? "
                + "ORDER BY u.user_id";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            String value =
                    "%" + keyword + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);
            statement.setString(5, value);
            statement.setString(6, value);
            statement.setString(7, value);
            statement.setString(8, value);

            try (
                ResultSet result =
                        statement.executeQuery()
            ) {

                while (result.next()) {

                    accounts.add(
                            createUserAccountFromResult(
                                    result));
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return accounts;
    }


    // =====================================================
    // 4. UPDATE USER ACCOUNT
    // Password is NOT changed here.
    // =====================================================
    public boolean updateUserAccount(
            int userId,
            int employeeId,
            String username,
            String role,
            String status) {

        if (userId <= 0
                || employeeId <= 0
                || username == null
                || username.trim().isEmpty()
                || !isValidRole(role)
                || !isValidStatus(status)) {

            return false;
        }

        if (usernameExists(
                username,
                userId)) {

            return false;
        }

        if (employeeHasAccount(
                employeeId,
                userId)) {

            return false;
        }

        String sql =
                "UPDATE user_account "
                + "SET employee_id = ?, "
                + "username = ?, "
                + "role = ?, "
                + "status = ? "
                + "WHERE user_id = ?";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    employeeId);

            statement.setString(
                    2,
                    username.trim());

            statement.setString(
                    3,
                    role.toUpperCase());

            statement.setString(
                    4,
                    status.toUpperCase());

            statement.setInt(
                    5,
                    userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // =====================================================
    // 5. RESET PASSWORD
    // =====================================================
    public boolean resetPassword(
            int userId,
            String newPassword) {

        if (userId <= 0
                || newPassword == null
                || newPassword.trim().isEmpty()) {

            return false;
        }

        String sql =
                "UPDATE user_account "
                + "SET password_hash = SHA2(?, 256) "
                + "WHERE user_id = ?";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    newPassword);

            statement.setInt(
                    2,
                    userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // =====================================================
    // 6. CHECK DUPLICATE USERNAME
    //
    // excludeUserId = 0 when creating a new account.
    // =====================================================
    public boolean usernameExists(
            String username,
            int excludeUserId) {

        String sql;

        if (excludeUserId > 0) {

            sql =
                    "SELECT user_id "
                    + "FROM user_account "
                    + "WHERE LOWER(username) = LOWER(?) "
                    + "AND user_id <> ? "
                    + "LIMIT 1";

        } else {

            sql =
                    "SELECT user_id "
                    + "FROM user_account "
                    + "WHERE LOWER(username) = LOWER(?) "
                    + "LIMIT 1";
        }

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    username.trim());

            if (excludeUserId > 0) {

                statement.setInt(
                        2,
                        excludeUserId);
            }

            try (
                ResultSet result =
                        statement.executeQuery()
            ) {

                return result.next();
            }

        } catch (Exception e) {

            e.printStackTrace();
            return true;
        }
    }


    // =====================================================
    // 7. CHECK IF EMPLOYEE ALREADY HAS USER ACCOUNT
    //
    // excludeUserId = 0 when adding.
    // =====================================================
    public boolean employeeHasAccount(
            int employeeId,
            int excludeUserId) {

        String sql;

        if (excludeUserId > 0) {

            sql =
                    "SELECT user_id "
                    + "FROM user_account "
                    + "WHERE employee_id = ? "
                    + "AND user_id <> ? "
                    + "LIMIT 1";

        } else {

            sql =
                    "SELECT user_id "
                    + "FROM user_account "
                    + "WHERE employee_id = ? "
                    + "LIMIT 1";
        }

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    employeeId);

            if (excludeUserId > 0) {

                statement.setInt(
                        2,
                        excludeUserId);
            }

            try (
                ResultSet result =
                        statement.executeQuery()
            ) {

                return result.next();
            }

        } catch (Exception e) {

            e.printStackTrace();
            return true;
        }
    }


    // =====================================================
    // 8. CHECK EMPLOYEE ACTIVE
    // =====================================================
    private boolean isEmployeeActive(
            int employeeId) {

        String sql =
                "SELECT employee_id "
                + "FROM employee "
                + "WHERE employee_id = ? "
                + "AND status = 'ACTIVE'";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    employeeId);

            try (
                ResultSet result =
                        statement.executeQuery()
            ) {

                return result.next();
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // =====================================================
    // ROLE VALIDATION
    // =====================================================
    private boolean isValidRole(
            String role) {

        return role != null
                && ("ADMIN".equalsIgnoreCase(role)
                || "EMPLOYEE".equalsIgnoreCase(role));
    }


    // =====================================================
    // STATUS VALIDATION
    // =====================================================
    private boolean isValidStatus(
            String status) {

        return status != null
                && ("ACTIVE".equalsIgnoreCase(status)
                || "INACTIVE".equalsIgnoreCase(status));
    }


    // =====================================================
    // RESULTSET -> USER ACCOUNT
    // =====================================================
    private UserAccount createUserAccountFromResult(
            ResultSet result) throws Exception {

        return new UserAccount(
                result.getInt("user_id"),
                result.getInt("employee_id"),
                result.getString("employee_name"),
                result.getString("username"),
                result.getString("role"),
                result.getString("status")
        );
    }
}