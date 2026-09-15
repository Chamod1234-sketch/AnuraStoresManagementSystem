package com.anurastores.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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

import com.anurastores.dao.SupplierDAO;
import com.anurastores.model.Supplier;

public class SupplierManagementFrame extends JFrame {

    private JTextField supplierIdField;
    private JTextField supplierNameField;
    private JTextField contactPersonField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField searchField;

    private JComboBox<String> statusCombo;

    private JTable supplierTable;
    private DefaultTableModel tableModel;

    private SupplierDAO supplierDAO;


    public SupplierManagementFrame() {

        supplierDAO =
                new SupplierDAO();

        setTitle(
                "Supplier Management - Anura Stores");

        setSize(
                1100,
                600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        loadSuppliers();
    }


    // =====================================================
    // UI
    // =====================================================

    private void initComponents() {

        // =================================================
        // FORM
        // =================================================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout());


        GridBagConstraints gbc =
                new GridBagConstraints();


        gbc.insets =
                new Insets(
                        5,
                        5,
                        5,
                        5);


        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        supplierIdField =
                new JTextField(15);

        supplierIdField.setEditable(false);


        supplierNameField =
                new JTextField(15);


        contactPersonField =
                new JTextField(15);


        phoneField =
                new JTextField(15);


        emailField =
                new JTextField(15);


        addressField =
                new JTextField(15);


        statusCombo =
                new JComboBox<String>(
                        new String[] {
                                "ACTIVE",
                                "INACTIVE"
                        });


        addRow(
                formPanel,
                gbc,
                0,
                "Supplier ID:",
                supplierIdField);


        addRow(
                formPanel,
                gbc,
                1,
                "Supplier Name:",
                supplierNameField);


        addRow(
                formPanel,
                gbc,
                2,
                "Contact Person:",
                contactPersonField);


        addRow(
                formPanel,
                gbc,
                3,
                "Phone:",
                phoneField);


        addRow(
                formPanel,
                gbc,
                4,
                "Email:",
                emailField);


        addRow(
                formPanel,
                gbc,
                5,
                "Address:",
                addressField);


        gbc.gridx = 0;
        gbc.gridy = 6;


        formPanel.add(
                new JLabel(
                        "Status:"),
                gbc);


        gbc.gridx = 1;


        formPanel.add(
                statusCombo,
                gbc);


        // =================================================
        // BUTTONS
        // =================================================

        JButton addButton =
                new JButton(
                        "Add");


        JButton updateButton =
                new JButton(
                        "Update");


        JButton deactivateButton =
                new JButton(
                        "Deactivate");


        JButton clearButton =
                new JButton(
                        "Clear");


        JButton manageProductsButton =
                new JButton(
                        "Manage Supplier Products");


        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                5,
                                10,
                                10));


        buttonPanel.add(
                addButton);

        buttonPanel.add(
                updateButton);

        buttonPanel.add(
                deactivateButton);

        buttonPanel.add(
                clearButton);

        buttonPanel.add(
                manageProductsButton);


        // =================================================
        // SEARCH
        // =================================================

        searchField =
                new JTextField(20);


        JButton searchButton =
                new JButton(
                        "Search");


        JButton showAllButton =
                new JButton(
                        "Show All");


        JPanel searchPanel =
                new JPanel();


        searchPanel.add(
                new JLabel(
                        "Search:"));

        searchPanel.add(
                searchField);

        searchPanel.add(
                searchButton);

        searchPanel.add(
                showAllButton);


        // =================================================
        // TABLE
        // =================================================

        String[] columns = {

                "Supplier ID",
                "Supplier Name",
                "Contact Person",
                "Phone",
                "Email",
                "Address",
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


        supplierTable =
                new JTable(
                        tableModel);


        JScrollPane scrollPane =
                new JScrollPane(
                        supplierTable);


        // =================================================
        // LAYOUT
        // =================================================

        JPanel leftPanel =
                new JPanel(
                        new BorderLayout());


        leftPanel.add(
                formPanel,
                BorderLayout.CENTER);


        leftPanel.add(
                buttonPanel,
                BorderLayout.SOUTH);


        add(
                searchPanel,
                BorderLayout.NORTH);


        add(
                leftPanel,
                BorderLayout.WEST);


        add(
                scrollPane,
                BorderLayout.CENTER);


        // =================================================
        // EVENTS
        // =================================================

        addButton.addActionListener(
                e -> addSupplier());


        updateButton.addActionListener(
                e -> updateSupplier());


        deactivateButton.addActionListener(
                e -> deactivateSupplier());


        clearButton.addActionListener(
                e -> clearFields());


        searchButton.addActionListener(
                e -> searchSuppliers());


        showAllButton.addActionListener(
                e -> {

                    searchField.setText("");

                    loadSuppliers();
                });


        manageProductsButton.addActionListener(
                e -> openSupplierProductManagement());


        supplierTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                fillFormFromTable();
                            }
                        });
    }


    // =====================================================
    // ADD FORM ROW
    // =====================================================

    private void addRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JTextField field) {

        gbc.gridx = 0;
        gbc.gridy = row;


        panel.add(
                new JLabel(label),
                gbc);


        gbc.gridx = 1;


        panel.add(
                field,
                gbc);
    }


    // =====================================================
    // OPEN SUPPLIER PRODUCT MANAGEMENT
    // =====================================================

    private void openSupplierProductManagement() {

        SupplierProductManagementFrame frame =
                new SupplierProductManagementFrame();


        frame.setVisible(true);
    }


    // =====================================================
    // ADD SUPPLIER
    // =====================================================

    private void addSupplier() {

        if (!validateFields()) {

            return;
        }


        String supplierName =
                supplierNameField
                        .getText()
                        .trim();


        String phone =
                phoneField
                        .getText()
                        .trim();


        if (supplierDAO.supplierExists(
                supplierName,
                phone,
                0)) {

            JOptionPane.showMessageDialog(
                    this,
                    "This supplier already exists.",
                    "Duplicate Supplier",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }


        Supplier supplier =
                getSupplierFromForm();


        boolean success =
                supplierDAO
                        .addSupplier(
                                supplier);


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier added successfully!");


            clearFields();

            loadSuppliers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not add supplier.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // =====================================================
    // UPDATE SUPPLIER
    // =====================================================

    private void updateSupplier() {

        if (supplierIdField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a supplier first.");

            return;
        }


        if (!validateFields()) {

            return;
        }


        int supplierId =
                Integer.parseInt(
                        supplierIdField
                                .getText());


        String supplierName =
                supplierNameField
                        .getText()
                        .trim();


        String phone =
                phoneField
                        .getText()
                        .trim();


        if (supplierDAO.supplierExists(
                supplierName,
                phone,
                supplierId)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Another supplier with these details already exists.",
                    "Duplicate Supplier",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }


        Supplier supplier =
                getSupplierFromForm();


        supplier.setSupplierId(
                supplierId);


        boolean success =
                supplierDAO
                        .updateSupplier(
                                supplier);


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier updated successfully!");


            clearFields();

            loadSuppliers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier update failed.");
        }
    }


    // =====================================================
    // DEACTIVATE SUPPLIER
    // =====================================================

    private void deactivateSupplier() {

        if (supplierIdField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a supplier first.");

            return;
        }


        String currentStatus =
                statusCombo
                        .getSelectedItem()
                        .toString();


        if ("INACTIVE".equalsIgnoreCase(
                currentStatus)) {

            JOptionPane.showMessageDialog(
                    this,
                    "This supplier is already inactive.");

            return;
        }


        int answer =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to deactivate this supplier?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION);


        if (answer !=
                JOptionPane.YES_OPTION) {

            return;
        }


        int supplierId =
                Integer.parseInt(
                        supplierIdField
                                .getText());


        boolean success =
                supplierDAO
                        .deactivateSupplier(
                                supplierId);


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier deactivated successfully!");


            clearFields();

            loadSuppliers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not deactivate supplier.");
        }
    }


    // =====================================================
    // FORM -> SUPPLIER
    // =====================================================

    private Supplier getSupplierFromForm() {

        Supplier supplier =
                new Supplier();


        supplier.setSupplierName(
                supplierNameField
                        .getText()
                        .trim());


        supplier.setContactPerson(
                contactPersonField
                        .getText()
                        .trim());


        supplier.setPhone(
                phoneField
                        .getText()
                        .trim());


        supplier.setEmail(
                emailField
                        .getText()
                        .trim());


        supplier.setAddress(
                addressField
                        .getText()
                        .trim());


        supplier.setStatus(
                statusCombo
                        .getSelectedItem()
                        .toString());


        return supplier;
    }


    // =====================================================
    // LOAD SUPPLIERS
    // =====================================================

    private void loadSuppliers() {

        List<Supplier> suppliers =
                supplierDAO
                        .getAllSuppliers();


        displaySuppliers(
                suppliers);
    }


    // =====================================================
    // SEARCH
    // =====================================================

    private void searchSuppliers() {

        String keyword =
                searchField
                        .getText()
                        .trim();


        if (keyword.isEmpty()) {

            loadSuppliers();

            return;
        }


        List<Supplier> suppliers =
                supplierDAO
                        .searchSuppliers(
                                keyword);


        displaySuppliers(
                suppliers);
    }


    // =====================================================
    // DISPLAY TABLE
    // =====================================================

    private void displaySuppliers(
            List<Supplier> suppliers) {

        tableModel.setRowCount(0);


        for (Supplier supplier :
                suppliers) {

            tableModel.addRow(
                    new Object[] {

                            supplier.getSupplierId(),
                            supplier.getSupplierName(),
                            supplier.getContactPerson(),
                            supplier.getPhone(),
                            supplier.getEmail(),
                            supplier.getAddress(),
                            supplier.getStatus()
                    });
        }
    }


    // =====================================================
    // TABLE -> FORM
    // =====================================================

    private void fillFormFromTable() {

        int row =
                supplierTable
                        .getSelectedRow();


        if (row == -1) {

            return;
        }


        supplierIdField.setText(
                getTableValue(
                        row,
                        0));


        supplierNameField.setText(
                getTableValue(
                        row,
                        1));


        contactPersonField.setText(
                getTableValue(
                        row,
                        2));


        phoneField.setText(
                getTableValue(
                        row,
                        3));


        emailField.setText(
                getTableValue(
                        row,
                        4));


        addressField.setText(
                getTableValue(
                        row,
                        5));


        statusCombo.setSelectedItem(
                getTableValue(
                        row,
                        6));
    }


    // =====================================================
    // TABLE VALUE
    // =====================================================

    private String getTableValue(
            int row,
            int column) {

        Object value =
                tableModel
                        .getValueAt(
                                row,
                                column);


        return value == null
                ? ""
                : value.toString();
    }


    // =====================================================
    // VALIDATION
    // =====================================================

    private boolean validateFields() {

        if (supplierNameField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier name is required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            return false;
        }


        if (phoneField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier phone number is required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);

            return false;
        }


        return true;
    }


    // =====================================================
    // CLEAR
    // =====================================================

    private void clearFields() {

        supplierIdField.setText("");

        supplierNameField.setText("");

        contactPersonField.setText("");

        phoneField.setText("");

        emailField.setText("");

        addressField.setText("");


        statusCombo.setSelectedItem(
                "ACTIVE");


        supplierTable.clearSelection();
    }


    // =====================================================
    // STANDALONE TEST
    // =====================================================

    public static void main(
            String[] args) {

        new SupplierManagementFrame()
                .setVisible(true);
    }
}