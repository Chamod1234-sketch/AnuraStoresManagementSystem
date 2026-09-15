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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.anurastores.dao.ProductDAO;
import com.anurastores.dao.SupplierDAO;
import com.anurastores.dao.SupplierProductDAO;
import com.anurastores.model.Product;
import com.anurastores.model.Supplier;
import com.anurastores.model.SupplierProduct;

public class SupplierProductManagementFrame extends JFrame {

    private SupplierDAO supplierDAO;
    private ProductDAO productDAO;
    private SupplierProductDAO supplierProductDAO;

    private JComboBox<SupplierItem> supplierCombo;
    private JComboBox<ProductItem> productCombo;

    private JTextField searchField;

    private JTable relationshipTable;
    private DefaultTableModel tableModel;


    public SupplierProductManagementFrame() {

        supplierDAO =
                new SupplierDAO();

        productDAO =
                new ProductDAO();

        supplierProductDAO =
                new SupplierProductDAO();

        setTitle(
                "Supplier Product Management - Anura Stores");

        setSize(
                950,
                600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        loadSuppliers();
        loadProducts();
        loadRelationships();
    }


    // =====================================================
    // UI
    // =====================================================

    private void initComponents() {

        setLayout(
                new BorderLayout(
                        10,
                        10));


        // =================================================
        // ASSIGNMENT AREA
        // =================================================

        JPanel assignmentPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                10,
                                10));


        assignmentPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Assign Product to Supplier"));


        supplierCombo =
                new JComboBox<SupplierItem>();


        productCombo =
                new JComboBox<ProductItem>();


        JButton assignButton =
                new JButton(
                        "Assign Product");


        JButton showSupplierButton =
                new JButton(
                        "Show Selected Supplier Products");


        assignmentPanel.add(
                new JLabel(
                        "Supplier:"));

        assignmentPanel.add(
                supplierCombo);


        assignmentPanel.add(
                new JLabel(
                        "Product:"));

        assignmentPanel.add(
                productCombo);


        assignmentPanel.add(
                assignButton);

        assignmentPanel.add(
                showSupplierButton);


        // =================================================
        // SEARCH AREA
        // =================================================

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


        JPanel northPanel =
                new JPanel(
                        new BorderLayout());


        northPanel.add(
                assignmentPanel,
                BorderLayout.NORTH);


        northPanel.add(
                searchPanel,
                BorderLayout.SOUTH);


        // =================================================
        // TABLE
        // =================================================

        String[] columns = {

                "Supplier ID",
                "Supplier Name",
                "Product ID",
                "Product Name"
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


        relationshipTable =
                new JTable(
                        tableModel);


        JScrollPane scrollPane =
                new JScrollPane(
                        relationshipTable);


        // =================================================
        // BOTTOM BUTTONS
        // =================================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout());


        JButton removeButton =
                new JButton(
                        "Remove Selected Association");


        JButton refreshButton =
                new JButton(
                        "Refresh");


        JButton closeButton =
                new JButton(
                        "Close");


        bottomPanel.add(
                removeButton);

        bottomPanel.add(
                refreshButton);

        bottomPanel.add(
                closeButton);


        // =================================================
        // ADD TO FRAME
        // =================================================

        add(
                northPanel,
                BorderLayout.NORTH);


        add(
                scrollPane,
                BorderLayout.CENTER);


        add(
                bottomPanel,
                BorderLayout.SOUTH);


        // =================================================
        // EVENTS
        // =================================================

        assignButton.addActionListener(
                e -> assignProduct());


        searchButton.addActionListener(
                e -> searchRelationships());


        showAllButton.addActionListener(
                e -> {

                    searchField.setText("");

                    loadRelationships();
                });


        showSupplierButton.addActionListener(
                e -> showSelectedSupplierProducts());


        removeButton.addActionListener(
                e -> removeSelectedAssociation());


        refreshButton.addActionListener(
                e -> refreshAll());


        closeButton.addActionListener(
                e -> dispose());


        relationshipTable
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        selectRelationshipFromTable();
                    }
                });
    }


    // =====================================================
    // LOAD ACTIVE SUPPLIERS
    // =====================================================

    private void loadSuppliers() {

        supplierCombo.removeAllItems();


        List<Supplier> suppliers =
                supplierDAO
                        .getAllSuppliers();


        for (Supplier supplier : suppliers) {

            if ("ACTIVE".equalsIgnoreCase(
                    supplier.getStatus())) {

                supplierCombo.addItem(
                        new SupplierItem(
                                supplier.getSupplierId(),
                                supplier.getSupplierName()
                        ));
            }
        }
    }


    // =====================================================
    // LOAD ACTIVE PRODUCTS
    // =====================================================

    private void loadProducts() {

        productCombo.removeAllItems();


        List<Product> products =
                productDAO
                        .getAllProducts();


        for (Product product : products) {

            if ("ACTIVE".equalsIgnoreCase(
                    product.getStatus())) {

                productCombo.addItem(
                        new ProductItem(
                                product.getProductId(),
                                product.getProductName()
                        ));
            }
        }
    }


    // =====================================================
    // LOAD ALL RELATIONSHIPS
    // =====================================================

    private void loadRelationships() {

        displayRelationships(
                supplierProductDAO
                        .getAllSupplierProducts());
    }


    // =====================================================
    // DISPLAY TABLE
    // =====================================================

    private void displayRelationships(
            List<SupplierProduct> list) {

        tableModel.setRowCount(0);


        for (SupplierProduct item : list) {

            tableModel.addRow(
                    new Object[] {

                            item.getSupplierId(),
                            item.getSupplierName(),
                            item.getProductId(),
                            item.getProductName()
                    });
        }
    }


    // =====================================================
    // ASSIGN PRODUCT
    // =====================================================

    private void assignProduct() {

        SupplierItem supplier =
                (SupplierItem)
                        supplierCombo
                                .getSelectedItem();


        ProductItem product =
                (ProductItem)
                        productCombo
                                .getSelectedItem();


        if (supplier == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a supplier.");

            return;
        }


        if (product == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product.");

            return;
        }


        if (supplierProductDAO.associationExists(
                supplier.getSupplierId(),
                product.getProductId())) {

            JOptionPane.showMessageDialog(
                    this,
                    "This product is already assigned "
                    + "to the selected supplier.",
                    "Duplicate Assignment",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }


        boolean success =
                supplierProductDAO
                        .assignProductToSupplier(
                                supplier.getSupplierId(),
                                product.getProductId());


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Product assigned to supplier successfully!");


            loadRelationships();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not assign product.\n"
                    + "Make sure both supplier and product are ACTIVE.",
                    "Assignment Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // =====================================================
    // SEARCH
    // =====================================================

    private void searchRelationships() {

        String keyword =
                searchField
                        .getText()
                        .trim();


        if (keyword.isEmpty()) {

            loadRelationships();

            return;
        }


        displayRelationships(
                supplierProductDAO
                        .searchSupplierProducts(
                                keyword));
    }


    // =====================================================
    // SHOW PRODUCTS FOR SELECTED SUPPLIER
    // =====================================================

    private void showSelectedSupplierProducts() {

        SupplierItem supplier =
                (SupplierItem)
                        supplierCombo
                                .getSelectedItem();


        if (supplier == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a supplier.");

            return;
        }


        displayRelationships(
                supplierProductDAO
                        .getProductsBySupplier(
                                supplier.getSupplierId()));
    }


    // =====================================================
    // REMOVE RELATIONSHIP
    // =====================================================

    private void removeSelectedAssociation() {

        int row =
                relationshipTable
                        .getSelectedRow();


        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a supplier-product "
                    + "relationship first.");

            return;
        }


        int supplierId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        0)
                                .toString());


        int productId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        2)
                                .toString());


        String supplierName =
                tableModel
                        .getValueAt(
                                row,
                                1)
                        .toString();


        String productName =
                tableModel
                        .getValueAt(
                                row,
                                3)
                        .toString();


        int answer =
                JOptionPane.showConfirmDialog(
                        this,
                        "Remove the relationship between:\n\n"
                        + "Supplier: "
                        + supplierName
                        + "\nProduct: "
                        + productName
                        + "\n\n"
                        + "The supplier and product records "
                        + "will NOT be deleted.",
                        "Confirm Remove",
                        JOptionPane.YES_NO_OPTION);


        if (answer !=
                JOptionPane.YES_OPTION) {

            return;
        }


        boolean success =
                supplierProductDAO
                        .removeSupplierProduct(
                                supplierId,
                                productId);


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier-product association removed successfully!");


            loadRelationships();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not remove association.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // =====================================================
    // TABLE SELECTION -> COMBOS
    // =====================================================

    private void selectRelationshipFromTable() {

        int row =
                relationshipTable
                        .getSelectedRow();


        if (row == -1) {
            return;
        }


        int supplierId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        0)
                                .toString());


        int productId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        row,
                                        2)
                                .toString());


        selectSupplier(
                supplierId);


        selectProduct(
                productId);
    }


    // =====================================================
    // SELECT SUPPLIER
    // =====================================================

    private void selectSupplier(
            int supplierId) {

        for (int i = 0;
                i < supplierCombo.getItemCount();
                i++) {

            SupplierItem item =
                    supplierCombo
                            .getItemAt(i);


            if (item.getSupplierId()
                    == supplierId) {

                supplierCombo
                        .setSelectedIndex(i);

                return;
            }
        }
    }


    // =====================================================
    // SELECT PRODUCT
    // =====================================================

    private void selectProduct(
            int productId) {

        for (int i = 0;
                i < productCombo.getItemCount();
                i++) {

            ProductItem item =
                    productCombo
                            .getItemAt(i);


            if (item.getProductId()
                    == productId) {

                productCombo
                        .setSelectedIndex(i);

                return;
            }
        }
    }


    // =====================================================
    // REFRESH ALL
    // =====================================================

    private void refreshAll() {

        loadSuppliers();
        loadProducts();
        loadRelationships();

        searchField.setText("");
    }


    // =====================================================
    // SUPPLIER COMBO ITEM
    // =====================================================

    private static class SupplierItem {

        private int supplierId;
        private String supplierName;


        public SupplierItem(
                int supplierId,
                String supplierName) {

            this.supplierId = supplierId;
            this.supplierName = supplierName;
        }


        public int getSupplierId() {

            return supplierId;
        }


        @Override
        public String toString() {

            return supplierId
                    + " - "
                    + supplierName;
        }
    }


    // =====================================================
    // PRODUCT COMBO ITEM
    // =====================================================

    private static class ProductItem {

        private int productId;
        private String productName;


        public ProductItem(
                int productId,
                String productName) {

            this.productId = productId;
            this.productName = productName;
        }


        public int getProductId() {

            return productId;
        }


        @Override
        public String toString() {

            return productId
                    + " - "
                    + productName;
        }
    }
}