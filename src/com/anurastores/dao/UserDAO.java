package com.anurastores.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.anurastores.model.User;
import com.anurastores.util.DBConnection;

public class UserDAO {

    public User authenticate(String username, String password) {

        String sql =
            "SELECT user_id, employee_id, username, role " +
            "FROM user_account " +
            "WHERE username = ? " +
            "AND password_hash = SHA2(?, 256) " +
            "AND status = 'ACTIVE'";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                return new User(
                    result.getInt("user_id"),
                    result.getInt("employee_id"),
                    result.getString("username"),
                    result.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}