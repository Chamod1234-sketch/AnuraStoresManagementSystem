package com.anurastores.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.anurastores.dao.EmployeeDAO;
import com.anurastores.dao.UserAccountDAO;
import com.anurastores.model.Employee;
import com.anurastores.model.User;
import com.anurastores.model.UserAccount;

public class UserAccountManagementFrame extends JFrame {

    private User loggedInUser;

    private UserAccountDAO userAccountDAO;
    private EmployeeDAO employeeDAO;

    private JTextField userIdField;
    private JComboBox<EmployeeItem> employeeCombo;
    private JTextField usernameField;

    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JComboBox<String> roleCombo;
    private JComboBox<String> statusCombo;

    private JTextField searchField;

    private JTable userTable;
    private DefaultTableModel tableModel;


    public UserAccountManagementFrame(User user) {

        this.loggedInUser = user;

        userAccountDAO =
                new UserAccountDAO();

        employeeDAO =
                new EmployeeDAO();

        setTitle(
                "User Account Management - Anura Stores");

        setSize(
                1050,
                650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        loadEmployees();
        loadUsers();
    }


    // =====================================================
    // UI
    // =====================================================

    private void initComponents() {

        setLayout(
                new BorderLayout(
                        10,
                        10));


        // -------------------------------------------------
        // FORM
        // -------------------------------------------------

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                7,
                                2,
                                10,
                                10));


        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "User Account Details"));


        userIdField =
                new JTextField();

        userIdField.setEditable(false);


        employeeCombo =
                new JComboBox<EmployeeItem>();


        usernameField =
                new JTextField();


        passwordField =
                new JPasswordField();


        confirmPasswordField =
                new JPasswordField();


        roleCombo =
                new JComboBox<String>(
                        new String[] {
                                "ADMIN",
                                "EMPLOYEE"
                        });


        statusCombo =
                new JComboBox<String>(
                        new String[] {
                                "ACTIVE",
                                "INACTIVE"
                        });


        formPanel.add(
                new JLabel(
                        "User ID:"));

        formPanel.add(
                userIdField);


        formPanel.add(
                new JLabel(
                        "Employee:"));

        formPanel.add(
                employeeCombo);


        formPanel.add(
                new JLabel(
                        "Username:"));

        formPanel.add(
                usernameField);


        formPanel.add(
                new JLabel(
                        "Password:"));

        formPanel.add(
                passwordField);


        formPanel.add(
                new JLabel(
                        "Confirm Password:"));

        formPanel.add(
                confirmPasswordField);


        formPanel.add(
                new JLabel(
                        "Role:"));

        formPanel.add(
                roleCombo);


        formPanel.add(
                new JLabel(
                        "Status:"));

        formPanel.add(
                statusCombo);


        // -------------------------------------------------
        // ACTION BUTTONS
        // -------------------------------------------------

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout());


        JButton addButton =
                new JButton(
                        "Add User");

        JButton updateButton =
                new JButton(
                        "Update User");

        JButton resetPasswordButton =
                new JButton(
                        "Reset Password");

        JButton clearButton =
                new JButton(
                        "Clear");


        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(resetPasswordButton);
        buttonPanel.add(clearButton);


        JPanel northPanel =
                new JPanel(
                        new BorderLayout());

        northPanel.add(
                formPanel,
                BorderLayout.CENTER);

        northPanel.add(
                buttonPanel,
                BorderLayout.SOUTH);


        // -------------------------------------------------
        // SEARCH
        // -------------------------------------------------

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout());


        searchField =
                new JTextField(20);


        JButton searchButton =
                new JButton(
                        "Search");

        JButton showAllButton =
                new JButton(
                        "Show All");


        searchPanel.add(
                new JLabel(
                        "Search:"));

        searchPanel.add(
                searchField);

        searchPanel.add(
                searchButton);

        searchPanel.add(
                showAllButton);


        // -------------------------------------------------
        // TABLE
        // -------------------------------------------------

        String[] columns = {

                "User ID",
                "Employee ID",
                "Employee Name",
                "Username",
                "Role",
                "Status"
        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };


        userTable =
                new JTable(
                        tableModel);


        JScrollPane scrollPane =
                new JScrollPane(
                        userTable);


        JPanel centerPanel =
                new JPanel(
                        new BorderLayout());


        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH);

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER);


        // -------------------------------------------------
        // ADD TO FRAME
        // -------------------------------------------------

        add(
                northPanel,
                BorderLayout.NORTH);

        add(
                centerPanel,
                BorderLayout.CENTER);


        // -------------------------------------------------
        // EVENTS
        // -------------------------------------------------

        addButton.addActionListener(
                e -> addUser());


        updateButton.addActionListener(
                e -> updateUser());


        resetPasswordButton.addActionListener(
                e -> resetPassword());


        clearButton.addActionListener(
                e -> clearForm());


        searchButton.addActionListener(
                e -> searchUsers());


        showAllButton.addActionListener(
                e -> {

                    searchField.setText("");

                    loadUsers();
                });


        userTable
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        fillFormFromTable();
                    }
                });
    }


    // =====================================================
    // LOAD EMPLOYEES
    // =====================================================

    private void loadEmployees() {

        employeeCombo.removeAllItems();


        List<Employee> employees =
                employeeDAO.getAllEmployees();


        for (Employee employee : employees) {

            employeeCombo.addItem(
                    new EmployeeItem(
                            employee.getEmployeeId(),

                            employee.getFirstName()
                            + " "
                            + employee.getLastName(),

                            employee.getStatus()
                    ));
        }
    }


    // =====================================================
    // LOAD USERS
    // =====================================================

    private void loadUsers() {

        displayUsers(
                userAccountDAO
                        .getAllUserAccounts());
    }


    // =====================================================
    // DISPLAY USERS
    // =====================================================

    private void displayUsers(
            List<UserAccount> accounts) {

        tableModel.setRowCount(0);


        for (UserAccount account : accounts) {

            tableModel.addRow(
                    new Object[] {

                            account.getUserId(),
                            account.getEmployeeId(),
                            account.getEmployeeName(),
                            account.getUsername(),
                            account.getRole(),
                            account.getStatus()
                    });
        }
    }


    // =====================================================
    // ADD USER
    // =====================================================

    private void addUser() {

        EmployeeItem employee =
                (EmployeeItem)
                        employeeCombo
                                .getSelectedItem();


        if (employee == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee.");

            return;
        }


        if (!"ACTIVE".equalsIgnoreCase(
                employee.getStatus())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Only ACTIVE employees can receive a new user account.");

            return;
        }


        String username =
                usernameField
                        .getText()
                        .trim();


        String password =
                new String(
                        passwordField
                                .getPassword());


        String confirmPassword =
                new String(
                        confirmPasswordField
                                .getPassword());


        String role =
                roleCombo
                        .getSelectedItem()
                        .toString();


        if (username.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username is required.");

            return;
        }


        if (password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password is required.");

            return;
        }


        if (password.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters.");

            return;
        }


        if (!password.equals(
                confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.");

            return;
        }


        boolean success =
                userAccountDAO
                        .addUserAccount(

                                employee.getEmployeeId(),
                                username,
                                password,
                                role
                        );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "User account created successfully!");


            clearForm();
            loadUsers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not create user account.\n"
                    + "Possible reasons:\n"
                    + "- Username already exists\n"
                    + "- Employee already has an account\n"
                    + "- Employee is inactive.",
                    "Create Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // =====================================================
    // UPDATE USER
    // =====================================================

    private void updateUser() {

        if (userIdField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user account first.");

            return;
        }


        EmployeeItem employee =
                (EmployeeItem)
                        employeeCombo
                                .getSelectedItem();


        if (employee == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee.");

            return;
        }


        int userId =
                Integer.parseInt(
                        userIdField
                                .getText());


        String username =
                usernameField
                        .getText()
                        .trim();


        String role =
                roleCombo
                        .getSelectedItem()
                        .toString();


        String status =
                statusCombo
                        .getSelectedItem()
                        .toString();


        if (username.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username is required.");

            return;
        }


        // Prevent currently logged-in admin
        // from disabling or removing own admin access.
        if (loggedInUser != null
                && userId
                == loggedInUser.getUserId()) {

            if (!"ADMIN".equalsIgnoreCase(
                    role)) {

                JOptionPane.showMessageDialog(
                        this,
                        "You cannot remove ADMIN role from "
                        + "your currently logged-in account.");

                return;
            }


            if (!"ACTIVE".equalsIgnoreCase(
                    status)) {

                JOptionPane.showMessageDialog(
                        this,
                        "You cannot deactivate your currently "
                        + "logged-in account.");

                return;
            }
        }


        boolean success =
                userAccountDAO
                        .updateUserAccount(

                                userId,
                                employee.getEmployeeId(),
                                username,
                                role,
                                status
                        );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "User account updated successfully!");


            clearForm();
            loadUsers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not update user account.\n"
                    + "Check duplicate username or employee account.",
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // =====================================================
    // RESET PASSWORD
    // =====================================================

    private void resetPassword() {

        if (userIdField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user account first.");

            return;
        }


        int userId =
                Integer.parseInt(
                        userIdField
                                .getText());


        JPasswordField newPasswordField =
                new JPasswordField();


        JPasswordField confirmField =
                new JPasswordField();


        Object[] message = {

                "New Password:",
                newPasswordField,

                "Confirm Password:",
                confirmField
        };


        int answer =
                JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Reset Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);


        if (answer
                != JOptionPane.OK_OPTION) {

            return;
        }


        String newPassword =
                new String(
                        newPasswordField
                                .getPassword());


        String confirmPassword =
                new String(
                        confirmField
                                .getPassword());


        if (newPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password cannot be empty.");

            return;
        }


        if (newPassword.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters.");

            return;
        }


        if (!newPassword.equals(
                confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.");

            return;
        }


        boolean success =
                userAccountDAO
                        .resetPassword(
                                userId,
                                newPassword);


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password reset successfully!");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Password reset failed.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // =====================================================
    // SEARCH USERS
    // =====================================================

    private void searchUsers() {

        String keyword =
                searchField
                        .getText()
                        .trim();


        if (keyword.isEmpty()) {

            loadUsers();

            return;
        }


        displayUsers(
                userAccountDAO
                        .searchUserAccounts(
                                keyword));
    }


    // =====================================================
    // TABLE -> FORM
    // =====================================================

    private void fillFormFromTable() {

        int row =
                userTable
                        .getSelectedRow();


        if (row == -1) {
            return;
        }


        int userId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        0)
                                .toString());


        int employeeId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        1)
                                .toString());


        String username =
                tableModel
                        .getValueAt(
                                row,
                                3)
                        .toString();


        String role =
                tableModel
                        .getValueAt(
                                row,
                                4)
                        .toString();


        String status =
                tableModel
                        .getValueAt(
                                row,
                                5)
                        .toString();


        userIdField.setText(
                String.valueOf(
                        userId));


        usernameField.setText(
                username);


        roleCombo.setSelectedItem(
                role);


        statusCombo.setSelectedItem(
                status);


        passwordField.setText("");
        confirmPasswordField.setText("");


        selectEmployee(
                employeeId);
    }


    // =====================================================
    // SELECT EMPLOYEE IN COMBO
    // =====================================================

    private void selectEmployee(
            int employeeId) {

        for (int i = 0;
                i < employeeCombo.getItemCount();
                i++) {

            EmployeeItem item =
                    employeeCombo
                            .getItemAt(i);


            if (item.getEmployeeId()
                    == employeeId) {

                employeeCombo
                        .setSelectedIndex(i);

                return;
            }
        }
    }


    // =====================================================
    // CLEAR
    // =====================================================

    private void clearForm() {

        userIdField.setText("");
        usernameField.setText("");

        passwordField.setText("");
        confirmPasswordField.setText("");

        roleCombo.setSelectedItem(
                "EMPLOYEE");

        statusCombo.setSelectedItem(
                "ACTIVE");


        if (employeeCombo
                .getItemCount()
                > 0) {

            employeeCombo
                    .setSelectedIndex(0);
        }


        userTable.clearSelection();
    }


    // =====================================================
    // EMPLOYEE COMBO ITEM
    // =====================================================

    private static class EmployeeItem {

        private int employeeId;
        private String employeeName;
        private String status;


        public EmployeeItem(
                int employeeId,
                String employeeName,
                String status) {

            this.employeeId =
                    employeeId;

            this.employeeName =
                    employeeName;

            this.status =
                    status;
        }


        public int getEmployeeId() {

            return employeeId;
        }


        public String getStatus() {

            return status;
        }


        @Override
        public String toString() {

            return employeeId
                    + " - "
                    + employeeName
                    + " ["
                    + status
                    + "]";
        }
    }
}