package com.anurastores.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
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

import com.anurastores.dao.CategoryDAO;
import com.anurastores.dao.ProductDAO;
import com.anurastores.model.Category;
import com.anurastores.model.Product;
import com.anurastores.model.User;

public class ProductManagementFrame extends JFrame {

    private JTextField productIdField;
    private JTextField productNameField;

    private JComboBox<Category> categoryCombo;

    private JTextField barcodeField;
    private JTextField sellingPriceField;
    private JTextField reorderLevelField;
    private JTextField expiryDateField;

    private JComboBox<String> statusCombo;

    private JTextField searchField;

    private JTable productTable;
    private DefaultTableModel tableModel;

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    private User loggedInUser;

    private JButton deactivateButton;


    public ProductManagementFrame(User user) {

        this.loggedInUser = user;

        productDAO =
                new ProductDAO();

        categoryDAO =
                new CategoryDAO();

        setTitle(
                "Product Management - Anura Stores");

        setSize(
                1200,
                650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        loadCategories();
        loadProducts();
    }


    // =====================================================
    // UI
    // =====================================================

    private void initComponents() {

        // =================================================
        // FORM PANEL
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


        productIdField =
                new JTextField(15);

        productIdField.setEditable(false);


        productNameField =
                new JTextField(15);


        categoryCombo =
                new JComboBox<Category>();


        barcodeField =
                new JTextField(15);


        sellingPriceField =
                new JTextField(15);


        reorderLevelField =
                new JTextField(15);


        expiryDateField =
                new JTextField(15);


        statusCombo =
                new JComboBox<String>(
                        new String[] {
                                "ACTIVE",
                                "INACTIVE"
                        });


        // -------------------------------------------------
        // PRODUCT ID
        // -------------------------------------------------

        addRow(
                formPanel,
                gbc,
                0,
                "Product ID:",
                productIdField);


        // -------------------------------------------------
        // PRODUCT NAME
        // -------------------------------------------------

        addRow(
                formPanel,
                gbc,
                1,
                "Product Name:",
                productNameField);


        // -------------------------------------------------
        // CATEGORY
        // -------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                new JLabel(
                        "Category:"),
                gbc);


        gbc.gridx = 1;

        formPanel.add(
                categoryCombo,
                gbc);


        // -------------------------------------------------
        // BARCODE
        // -------------------------------------------------

        addRow(
                formPanel,
                gbc,
                3,
                "Barcode:",
                barcodeField);


        // -------------------------------------------------
        // SELLING PRICE
        // -------------------------------------------------

        addRow(
                formPanel,
                gbc,
                4,
                "Selling Price:",
                sellingPriceField);


        // -------------------------------------------------
        // REORDER LEVEL
        // -------------------------------------------------

        addRow(
                formPanel,
                gbc,
                5,
                "Reorder Level:",
                reorderLevelField);


        // -------------------------------------------------
        // EXPIRY DATE
        // -------------------------------------------------

        addRow(
                formPanel,
                gbc,
                6,
                "Expiry Date (YYYY-MM-DD):",
                expiryDateField);


        // -------------------------------------------------
        // STATUS
        // -------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 7;

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


        deactivateButton =
                new JButton(
                        "Deactivate");


        JButton clearButton =
                new JButton(
                        "Clear");


        JButton manageCategoriesButton =
                new JButton(
                        "Manage Categories");


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
                manageCategoriesButton);


        // =================================================
        // PRODUCT DEACTIVATE = ADMIN ONLY
        // =================================================

        if (loggedInUser == null
                || !"ADMIN".equalsIgnoreCase(
                        loggedInUser.getRole())) {

            deactivateButton.setEnabled(false);
        }


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

                "Product ID",
                "Product Name",
                "Category",
                "Barcode",
                "Selling Price",
                "Reorder Level",
                "Expiry Date",
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


        productTable =
                new JTable(
                        tableModel);


        JScrollPane scrollPane =
                new JScrollPane(
                        productTable);


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
                e -> addProduct());


        updateButton.addActionListener(
                e -> updateProduct());


        deactivateButton.addActionListener(
                e -> deactivateProduct());


        clearButton.addActionListener(
                e -> clearFields());


        searchButton.addActionListener(
                e -> searchProducts());


        showAllButton.addActionListener(
                e -> {

                    searchField.setText("");

                    loadProducts();
                });


        // =================================================
        // MANAGE CATEGORIES
        // =================================================

        manageCategoriesButton.addActionListener(
                e -> openCategoryManagement());


        productTable
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
    // OPEN CATEGORY MANAGEMENT
    // =====================================================

    private void openCategoryManagement() {

        CategoryManagementFrame categoryFrame =
                new CategoryManagementFrame();


        // When Category Management closes,
        // refresh category dropdown and products.
        categoryFrame.addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosed(
                            WindowEvent e) {

                        loadCategories();
                        loadProducts();
                    }
                });


        categoryFrame.setVisible(true);
    }


    // =====================================================
    // LOAD ACTIVE CATEGORIES ONLY
    // =====================================================

    private void loadCategories() {

        categoryCombo.removeAllItems();


        List<Category> categories =
                categoryDAO
                        .getActiveCategories();


        for (Category category :
                categories) {

            categoryCombo.addItem(
                    category);
        }
    }


    // =====================================================
    // ADD PRODUCT
    // =====================================================

    private void addProduct() {

        try {

            Product product =
                    getProductFromForm();


            if (product == null) {

                return;
            }


            if (productDAO.barcodeExists(
                    product.getBarcode(),
                    0)) {

                JOptionPane.showMessageDialog(
                        this,
                        "This barcode is already used by another product.",
                        "Duplicate Barcode",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }


            boolean success =
                    productDAO
                            .addProduct(
                                    product);


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Product added successfully!");


                clearFields();

                loadProducts();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Could not add product.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the entered product information.",
                    "Invalid Data",
                    JOptionPane.WARNING_MESSAGE);
        }
    }


    // =====================================================
    // UPDATE PRODUCT
    // =====================================================

    private void updateProduct() {

        if (productIdField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product first.");

            return;
        }


        try {

            Product product =
                    getProductFromForm();


            if (product == null) {

                return;
            }


            int productId =
                    Integer.parseInt(
                            productIdField
                                    .getText());


            product.setProductId(
                    productId);


            if (productDAO.barcodeExists(
                    product.getBarcode(),
                    productId)) {

                JOptionPane.showMessageDialog(
                        this,
                        "This barcode is already used by another product.",
                        "Duplicate Barcode",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }


            boolean success =
                    productDAO
                            .updateProduct(
                                    product);


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Product updated successfully!");


                clearFields();

                loadProducts();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Product update failed.");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please check the entered product information.",
                    "Invalid Data",
                    JOptionPane.WARNING_MESSAGE);
        }
    }


    // =====================================================
    // DEACTIVATE PRODUCT
    // =====================================================

    private void deactivateProduct() {

        if (productIdField
                .getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product first.");

            return;
        }


        int answer =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to deactivate this product?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION);


        if (answer !=
                JOptionPane.YES_OPTION) {

            return;
        }


        int productId =
                Integer.parseInt(
                        productIdField
                                .getText());


        boolean success =
                productDAO
                        .deactivateProduct(
                                productId);


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Product deactivated successfully!");


            clearFields();

            loadProducts();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not deactivate product.");
        }
    }


    // =====================================================
    // FORM -> PRODUCT OBJECT
    // =====================================================

    private Product getProductFromForm() {

        String productName =
                productNameField
                        .getText()
                        .trim();


        if (productName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Product name is required.");

            return null;
        }


        // -------------------------------------------------
        // CATEGORY
        // -------------------------------------------------

        Category category =
                (Category)
                        categoryCombo
                                .getSelectedItem();


        if (category == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an ACTIVE category.");

            return null;
        }


        // -------------------------------------------------
        // PRICE
        // -------------------------------------------------

        String priceText =
                sellingPriceField
                        .getText()
                        .trim();


        if (priceText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selling price is required.");

            return null;
        }


        BigDecimal sellingPrice;


        try {

            sellingPrice =
                    new BigDecimal(
                            priceText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selling price must be a valid number.");

            return null;
        }


        if (sellingPrice.compareTo(
                BigDecimal.ZERO)
                <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selling price must be greater than zero.");

            return null;
        }


        // -------------------------------------------------
        // REORDER LEVEL
        // -------------------------------------------------

        int reorderLevel = 0;


        String reorderText =
                reorderLevelField
                        .getText()
                        .trim();


        if (!reorderText.isEmpty()) {

            try {

                reorderLevel =
                        Integer.parseInt(
                                reorderText);

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Reorder level must be a whole number.");

                return null;
            }
        }


        if (reorderLevel < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Reorder level cannot be negative.");

            return null;
        }


        // -------------------------------------------------
        // EXPIRY DATE
        // -------------------------------------------------

        Date expiryDate = null;


        String expiryText =
                expiryDateField
                        .getText()
                        .trim();


        if (!expiryText.isEmpty()) {

            try {

                expiryDate =
                        Date.valueOf(
                                expiryText);

            } catch (IllegalArgumentException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Expiry date must be in YYYY-MM-DD format.");

                return null;
            }
        }


        // -------------------------------------------------
        // CREATE PRODUCT OBJECT
        // -------------------------------------------------

        Product product =
                new Product();


        product.setProductName(
                productName);


        product.setCategoryId(
                category.getCategoryId());


        product.setCategoryName(
                category.getCategoryName());


        String barcode =
                barcodeField
                        .getText()
                        .trim();


        product.setBarcode(
                barcode);


        product.setSellingPrice(
                sellingPrice);


        product.setReorderLevel(
                reorderLevel);


        product.setExpiryDate(
                expiryDate);


        product.setStatus(
                statusCombo
                        .getSelectedItem()
                        .toString());


        return product;
    }


    // =====================================================
    // LOAD ALL PRODUCTS
    // =====================================================

    private void loadProducts() {

        List<Product> products =
                productDAO
                        .getAllProducts();


        displayProducts(
                products);
    }


    // =====================================================
    // SEARCH PRODUCTS
    // =====================================================

    private void searchProducts() {

        String keyword =
                searchField
                        .getText()
                        .trim();


        if (keyword.isEmpty()) {

            loadProducts();

            return;
        }


        List<Product> products =
                productDAO
                        .searchProducts(
                                keyword);


        displayProducts(
                products);
    }


    // =====================================================
    // DISPLAY PRODUCTS
    // =====================================================

    private void displayProducts(
            List<Product> products) {

        tableModel.setRowCount(0);


        for (Product product :
                products) {

            tableModel.addRow(
                    new Object[] {

                            product.getProductId(),
                            product.getProductName(),
                            product.getCategoryName(),
                            product.getBarcode(),
                            product.getSellingPrice(),
                            product.getReorderLevel(),
                            product.getExpiryDate(),
                            product.getStatus()
                    });
        }
    }


    // =====================================================
    // TABLE -> FORM
    // =====================================================

    private void fillFormFromTable() {

        int row =
                productTable
                        .getSelectedRow();


        if (row == -1) {

            return;
        }


        productIdField.setText(
                getTableValue(
                        row,
                        0));


        productNameField.setText(
                getTableValue(
                        row,
                        1));


        // Important:
        // Only ACTIVE categories are in the combo.
        // If this product belongs to an INACTIVE category,
        // no category will be selected.
        selectCategory(
                getTableValue(
                        row,
                        2));


        barcodeField.setText(
                getTableValue(
                        row,
                        3));


        sellingPriceField.setText(
                getTableValue(
                        row,
                        4));


        reorderLevelField.setText(
                getTableValue(
                        row,
                        5));


        expiryDateField.setText(
                getTableValue(
                        row,
                        6));


        statusCombo.setSelectedItem(
                getTableValue(
                        row,
                        7));
    }


    // =====================================================
    // SELECT CATEGORY
    // =====================================================

    private void selectCategory(
            String categoryName) {

        // Clear previous selection first.
        // This prevents accidentally selecting
        // another category if the product's
        // category is currently INACTIVE.
        categoryCombo.setSelectedItem(null);


        for (int i = 0;
                i < categoryCombo
                        .getItemCount();
                i++) {

            Category category =
                    categoryCombo
                            .getItemAt(i);


            if (category
                    .getCategoryName()
                    .equalsIgnoreCase(
                            categoryName)) {

                categoryCombo
                        .setSelectedIndex(i);

                return;
            }
        }
    }


    // =====================================================
    // GET TABLE VALUE
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
    // CLEAR
    // =====================================================

    private void clearFields() {

        productIdField.setText("");

        productNameField.setText("");

        barcodeField.setText("");

        sellingPriceField.setText("");

        reorderLevelField.setText("");

        expiryDateField.setText("");


        statusCombo.setSelectedItem(
                "ACTIVE");


        if (categoryCombo
                .getItemCount()
                > 0) {

            categoryCombo
                    .setSelectedIndex(0);
        }


        productTable.clearSelection();
    }
}