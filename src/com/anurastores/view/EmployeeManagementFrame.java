package com.anurastores.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.anurastores.dao.EmployeeDAO;
import com.anurastores.model.Employee;

public class EmployeeManagementFrame extends JFrame {

    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField nicField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField positionField;
    private JTextField hireDateField;
    private JTextField searchField;

    private JComboBox<String> statusCombo;

    private JTable employeeTable;
    private DefaultTableModel tableModel;

    private EmployeeDAO employeeDAO;

    public EmployeeManagementFrame() {

        employeeDAO = new EmployeeDAO();

        setTitle("Employee Management - Anura Stores");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        loadEmployees();
    }

    private void initComponents() {

        JPanel formPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = new JTextField(15);
        idField.setEditable(false);

        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        nicField = new JTextField(15);
        phoneField = new JTextField(15);
        emailField = new JTextField(15);
        addressField = new JTextField(15);
        positionField = new JTextField(15);
        hireDateField = new JTextField(15);

        statusCombo = new JComboBox<String>(
                new String[] {"ACTIVE", "INACTIVE"});

        addFormRow(formPanel, gbc, 0,
                "Employee ID:", idField);

        addFormRow(formPanel, gbc, 1,
                "First Name:", firstNameField);

        addFormRow(formPanel, gbc, 2,
                "Last Name:", lastNameField);

        addFormRow(formPanel, gbc, 3,
                "NIC:", nicField);

        addFormRow(formPanel, gbc, 4,
                "Phone:", phoneField);

        addFormRow(formPanel, gbc, 5,
                "Email:", emailField);

        addFormRow(formPanel, gbc, 6,
                "Address:", addressField);

        addFormRow(formPanel, gbc, 7,
                "Position:", positionField);

        addFormRow(formPanel, gbc, 8,
                "Hire Date (YYYY-MM-DD):", hireDateField);

        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        formPanel.add(statusCombo, gbc);


        // BUTTONS

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deactivateButton = new JButton("Deactivate");
        JButton clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 4, 10, 10));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deactivateButton);
        buttonPanel.add(clearButton);


        // SEARCH

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");

        JPanel searchPanel = new JPanel();

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);


        // TABLE

        String[] columns = {
                "ID",
                "First Name",
                "Last Name",
                "NIC",
                "Phone",
                "Email",
                "Address",
                "Position",
                "Hire Date",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(employeeTable);


        JPanel leftPanel = new JPanel(new BorderLayout());

        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);


        add(searchPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);


        // EVENTS

        addButton.addActionListener(e -> addEmployee());

        updateButton.addActionListener(e -> updateEmployee());

        deactivateButton.addActionListener(
                e -> deactivateEmployee());

        clearButton.addActionListener(
                e -> clearFields());

        searchButton.addActionListener(
                e -> searchEmployees());

        showAllButton.addActionListener(e -> {
            searchField.setText("");
            loadEmployees();
        });


        employeeTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        fillFormFromTable();
                    }
                });
    }


    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JTextField field) {

        gbc.gridx = 0;
        gbc.gridy = row;

        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;

        panel.add(field, gbc);
    }


    // ADD EMPLOYEE

    private void addEmployee() {

        try {

            if (!validateFields()) {
                return;
            }

            Employee employee = new Employee();

            employee.setFirstName(
                    firstNameField.getText().trim());

            employee.setLastName(
                    lastNameField.getText().trim());

            employee.setNic(
                    nicField.getText().trim());

            employee.setPhone(
                    phoneField.getText().trim());

            employee.setEmail(
                    emailField.getText().trim());

            employee.setAddress(
                    addressField.getText().trim());

            employee.setPosition(
                    positionField.getText().trim());

            employee.setHireDate(
                    Date.valueOf(hireDateField.getText().trim()));

            employee.setStatus(
                    statusCombo.getSelectedItem().toString());

            boolean success =
                    employeeDAO.addEmployee(employee);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Employee added successfully!");

                clearFields();
                loadEmployees();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Could not add employee.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Hire date must be in YYYY-MM-DD format.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE);
        }
    }


    // UPDATE EMPLOYEE

    private void updateEmployee() {

        if (idField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee first.");

            return;
        }

        try {

            if (!validateFields()) {
                return;
            }

            Employee employee = new Employee();

            employee.setEmployeeId(
                    Integer.parseInt(idField.getText()));

            employee.setFirstName(
                    firstNameField.getText().trim());

            employee.setLastName(
                    lastNameField.getText().trim());

            employee.setNic(
                    nicField.getText().trim());

            employee.setPhone(
                    phoneField.getText().trim());

            employee.setEmail(
                    emailField.getText().trim());

            employee.setAddress(
                    addressField.getText().trim());

            employee.setPosition(
                    positionField.getText().trim());

            employee.setHireDate(
                    Date.valueOf(hireDateField.getText().trim()));

            employee.setStatus(
                    statusCombo.getSelectedItem().toString());

            boolean success =
                    employeeDAO.updateEmployee(employee);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Employee updated successfully!");

                clearFields();
                loadEmployees();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Employee update failed.");
            }

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Hire date must be in YYYY-MM-DD format.");
        }
    }


    // DEACTIVATE

    private void deactivateEmployee() {

        if (idField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee first.");

            return;
        }

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Do you want to deactivate this employee?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {

            int employeeId =
                    Integer.parseInt(idField.getText());

            if (employeeDAO.deactivateEmployee(employeeId)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Employee deactivated successfully!");

                clearFields();
                loadEmployees();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Could not deactivate employee.");
            }
        }
    }


    // LOAD ALL

    private void loadEmployees() {

        List<Employee> employees =
                employeeDAO.getAllEmployees();

        displayEmployees(employees);
    }


    // SEARCH

    private void searchEmployees() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadEmployees();
            return;
        }

        List<Employee> employees =
                employeeDAO.searchEmployees(keyword);

        displayEmployees(employees);
    }


    // DISPLAY TABLE

    private void displayEmployees(
            List<Employee> employees) {

        tableModel.setRowCount(0);

        for (Employee employee : employees) {

            tableModel.addRow(new Object[] {

                    employee.getEmployeeId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getNic(),
                    employee.getPhone(),
                    employee.getEmail(),
                    employee.getAddress(),
                    employee.getPosition(),
                    employee.getHireDate(),
                    employee.getStatus()
            });
        }
    }


    // CLICK TABLE ROW

    private void fillFormFromTable() {

        int selectedRow =
                employeeTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        idField.setText(
                tableModel.getValueAt(
                        selectedRow, 0).toString());

        firstNameField.setText(
                getTableValue(selectedRow, 1));

        lastNameField.setText(
                getTableValue(selectedRow, 2));

        nicField.setText(
                getTableValue(selectedRow, 3));

        phoneField.setText(
                getTableValue(selectedRow, 4));

        emailField.setText(
                getTableValue(selectedRow, 5));

        addressField.setText(
                getTableValue(selectedRow, 6));

        positionField.setText(
                getTableValue(selectedRow, 7));

        hireDateField.setText(
                getTableValue(selectedRow, 8));

        statusCombo.setSelectedItem(
                getTableValue(selectedRow, 9));
    }


    private String getTableValue(
            int row,
            int column) {

        Object value =
                tableModel.getValueAt(row, column);

        return value == null
                ? ""
                : value.toString();
    }


    // VALIDATION

    private boolean validateFields() {

        if (firstNameField.getText().trim().isEmpty()
                || lastNameField.getText().trim().isEmpty()
                || positionField.getText().trim().isEmpty()
                || hireDateField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "First Name, Last Name, Position and Hire Date are required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            return false;
        }

        return true;
    }


    // CLEAR

    private void clearFields() {

        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        nicField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        positionField.setText("");
        hireDateField.setText("");

        statusCombo.setSelectedItem("ACTIVE");

        employeeTable.clearSelection();
    }


    public static void main(String[] args) {

        new EmployeeManagementFrame().setVisible(true);
    }
}