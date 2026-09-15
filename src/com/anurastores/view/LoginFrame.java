package com.anurastores.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.anurastores.dao.UserDAO;
import com.anurastores.model.User;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {

        setTitle("Anura Stores Management System - Login");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel =
                new JLabel("ANURA STORES LOGIN");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        JLabel usernameLabel =
                new JLabel("Username:");

        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(usernameLabel, gbc);

        usernameField =
                new JTextField(18);

        gbc.gridx = 1;

        panel.add(usernameField, gbc);

        JLabel passwordLabel =
                new JLabel("Password:");

        gbc.gridx = 0;
        gbc.gridy = 2;

        panel.add(passwordLabel, gbc);

        passwordField =
                new JPasswordField(18);

        gbc.gridx = 1;

        panel.add(passwordField, gbc);

        loginButton =
                new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        panel.add(loginButton, gbc);

        add(panel);

        loginButton.addActionListener(e -> login());

        passwordField.addActionListener(e -> login());
    }

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        UserDAO userDAO =
                new UserDAO();

        User user =
                userDAO.authenticate(username, password);

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!");

            dispose();

            DashboardFrame dashboard =
                    new DashboardFrame(user);

            dashboard.setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);

            passwordField.setText("");
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
        	

            new LoginFrame().setVisible(true);

        });
    }
}