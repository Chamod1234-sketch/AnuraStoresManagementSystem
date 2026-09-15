package com.anurastores.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.anurastores.model.Product;
import com.anurastores.util.DBConnection;

public class ProductDAO {

    // 1. ADD PRODUCT
    public boolean addProduct(Product product) {

        String sql = "INSERT INTO product "
                + "(product_name, category_id, barcode, selling_price, "
                + "reorder_level, expiry_date, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    product.getProductName());

            statement.setInt(
                    2,
                    product.getCategoryId());

            if (product.getBarcode() == null
                    || product.getBarcode().trim().isEmpty()) {

                statement.setNull(
                        3,
                        Types.VARCHAR);

            } else {

                statement.setString(
                        3,
                        product.getBarcode().trim());
            }

            statement.setBigDecimal(
                    4,
                    product.getSellingPrice());

            statement.setInt(
                    5,
                    product.getReorderLevel());

            if (product.getExpiryDate() != null) {

                statement.setDate(
                        6,
                        product.getExpiryDate());

            } else {

                statement.setNull(
                        6,
                        Types.DATE);
            }

            statement.setString(
                    7,
                    product.getStatus());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // 2. VIEW ALL PRODUCTS
    public List<Product> getAllProducts() {

        List<Product> products =
                new ArrayList<Product>();

        String sql =
                "SELECT p.product_id, "
                + "p.product_name, "
                + "p.category_id, "
                + "c.category_name, "
                + "p.barcode, "
                + "p.selling_price, "
                + "p.reorder_level, "
                + "p.expiry_date, "
                + "p.status "
                + "FROM product p "
                + "INNER JOIN category c "
                + "ON p.category_id = c.category_id "
                + "ORDER BY p.product_id DESC";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result =
                    statement.executeQuery()
        ) {

            while (result.next()) {

                products.add(
                        createProductFromResult(result)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }


    // 3. SEARCH PRODUCTS
    public List<Product> searchProducts(String keyword) {

        List<Product> products =
                new ArrayList<Product>();

        String sql =
                "SELECT p.product_id, "
                + "p.product_name, "
                + "p.category_id, "
                + "c.category_name, "
                + "p.barcode, "
                + "p.selling_price, "
                + "p.reorder_level, "
                + "p.expiry_date, "
                + "p.status "
                + "FROM product p "
                + "INNER JOIN category c "
                + "ON p.category_id = c.category_id "
                + "WHERE CAST(p.product_id AS CHAR) LIKE ? "
                + "OR p.product_name LIKE ? "
                + "OR p.barcode LIKE ? "
                + "OR c.category_name LIKE ? "
                + "OR p.status LIKE ? "
                + "ORDER BY p.product_id DESC";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            String searchValue =
                    "%" + keyword + "%";

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);
            statement.setString(3, searchValue);
            statement.setString(4, searchValue);
            statement.setString(5, searchValue);

            try (
                ResultSet result =
                        statement.executeQuery()
            ) {

                while (result.next()) {

                    products.add(
                            createProductFromResult(result)
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }


    // 4. UPDATE PRODUCT
    public boolean updateProduct(Product product) {

        String sql =
                "UPDATE product SET "
                + "product_name = ?, "
                + "category_id = ?, "
                + "barcode = ?, "
                + "selling_price = ?, "
                + "reorder_level = ?, "
                + "expiry_date = ?, "
                + "status = ? "
                + "WHERE product_id = ?";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    product.getProductName());

            statement.setInt(
                    2,
                    product.getCategoryId());

            if (product.getBarcode() == null
                    || product.getBarcode().trim().isEmpty()) {

                statement.setNull(
                        3,
                        Types.VARCHAR);

            } else {

                statement.setString(
                        3,
                        product.getBarcode().trim());
            }

            statement.setBigDecimal(
                    4,
                    product.getSellingPrice());

            statement.setInt(
                    5,
                    product.getReorderLevel());

            if (product.getExpiryDate() != null) {

                statement.setDate(
                        6,
                        product.getExpiryDate());

            } else {

                statement.setNull(
                        6,
                        Types.DATE);
            }

            statement.setString(
                    7,
                    product.getStatus());

            statement.setInt(
                    8,
                    product.getProductId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // 5. DEACTIVATE PRODUCT
    public boolean deactivateProduct(int productId) {

        String sql =
                "UPDATE product "
                + "SET status = 'INACTIVE' "
                + "WHERE product_id = ?";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    productId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // 6. CHECK DUPLICATE BARCODE
    public boolean barcodeExists(
            String barcode,
            int excludeProductId) {

        if (barcode == null
                || barcode.trim().isEmpty()) {

            return false;
        }

        String sql =
                "SELECT product_id "
                + "FROM product "
                + "WHERE barcode = ? "
                + "AND product_id <> ?";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    barcode.trim());

            statement.setInt(
                    2,
                    excludeProductId);

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


    // RESULTSET -> PRODUCT OBJECT
    private Product createProductFromResult(
            ResultSet result) throws Exception {

        return new Product(
                result.getInt("product_id"),
                result.getString("product_name"),
                result.getInt("category_id"),
                result.getString("category_name"),
                result.getString("barcode"),
                result.getBigDecimal("selling_price"),
                result.getInt("reorder_level"),
                result.getDate("expiry_date"),
                result.getString("status")
        );
    }
}