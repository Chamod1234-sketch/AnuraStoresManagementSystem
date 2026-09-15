package com.anurastores.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.anurastores.model.Category;
import com.anurastores.util.DBConnection;

public class CategoryDAO {

    // =====================================================
    // 1. VIEW ALL CATEGORIES
    // =====================================================
    public List<Category> getAllCategories() {

        List<Category> categories =
                new ArrayList<Category>();

        String sql =
                "SELECT category_id, "
                + "category_name, "
                + "description, "
                + "status "
                + "FROM category "
                + "ORDER BY category_name";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result =
                    statement.executeQuery()
        ) {

            while (result.next()) {

                categories.add(
                        createCategoryFromResult(result));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return categories;
    }


    // =====================================================
    // 2. VIEW ACTIVE CATEGORIES
    // Useful for Product Management dropdown
    // =====================================================
    public List<Category> getActiveCategories() {

        List<Category> categories =
                new ArrayList<Category>();

        String sql =
                "SELECT category_id, "
                + "category_name, "
                + "description, "
                + "status "
                + "FROM category "
                + "WHERE status = 'ACTIVE' "
                + "ORDER BY category_name";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result =
                    statement.executeQuery()
        ) {

            while (result.next()) {

                categories.add(
                        createCategoryFromResult(result));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return categories;
    }


    // =====================================================
    // 3. ADD CATEGORY
    // =====================================================
    public boolean addCategory(
            String categoryName,
            String description) {

        if (categoryName == null
                || categoryName.trim().isEmpty()) {

            return false;
        }

        if (categoryNameExists(
                categoryName,
                0)) {

            return false;
        }

        String sql =
                "INSERT INTO category "
                + "(category_name, description, status) "
                + "VALUES (?, ?, 'ACTIVE')";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    categoryName.trim());

            statement.setString(
                    2,
                    emptyToNull(description));

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // =====================================================
    // 4. SEARCH CATEGORIES
    // ID / Name / Description / Status
    // =====================================================
    public List<Category> searchCategories(
            String keyword) {

        List<Category> categories =
                new ArrayList<Category>();

        String sql =
                "SELECT category_id, "
                + "category_name, "
                + "description, "
                + "status "
                + "FROM category "
                + "WHERE CAST(category_id AS CHAR) LIKE ? "
                + "OR category_name LIKE ? "
                + "OR description LIKE ? "
                + "OR status LIKE ? "
                + "ORDER BY category_name";

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

            try (
                ResultSet result =
                        statement.executeQuery()
            ) {

                while (result.next()) {

                    categories.add(
                            createCategoryFromResult(
                                    result));
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return categories;
    }


    // =====================================================
    // 5. UPDATE CATEGORY
    // =====================================================
    public boolean updateCategory(
            int categoryId,
            String categoryName,
            String description,
            String status) {

        if (categoryId <= 0
                || categoryName == null
                || categoryName.trim().isEmpty()) {

            return false;
        }

        if (!isValidStatus(status)) {

            return false;
        }

        if (categoryNameExists(
                categoryName,
                categoryId)) {

            return false;
        }

        String sql =
                "UPDATE category "
                + "SET category_name = ?, "
                + "description = ?, "
                + "status = ? "
                + "WHERE category_id = ?";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    categoryName.trim());

            statement.setString(
                    2,
                    emptyToNull(description));

            statement.setString(
                    3,
                    status.toUpperCase());

            statement.setInt(
                    4,
                    categoryId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // =====================================================
    // 6. DEACTIVATE CATEGORY
    // Soft delete
    // =====================================================
    public boolean deactivateCategory(
            int categoryId) {

        if (categoryId <= 0) {

            return false;
        }

        String sql =
                "UPDATE category "
                + "SET status = 'INACTIVE' "
                + "WHERE category_id = ?";

        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    categoryId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // =====================================================
    // 7. DUPLICATE CATEGORY NAME CHECK
    // excludeCategoryId = 0 when adding
    // =====================================================
    public boolean categoryNameExists(
            String categoryName,
            int excludeCategoryId) {

        String sql;

        if (excludeCategoryId > 0) {

            sql =
                    "SELECT category_id "
                    + "FROM category "
                    + "WHERE LOWER(category_name) = LOWER(?) "
                    + "AND category_id <> ? "
                    + "LIMIT 1";

        } else {

            sql =
                    "SELECT category_id "
                    + "FROM category "
                    + "WHERE LOWER(category_name) = LOWER(?) "
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
                    categoryName.trim());

            if (excludeCategoryId > 0) {

                statement.setInt(
                        2,
                        excludeCategoryId);
            }

            try (
                ResultSet result =
                        statement.executeQuery()
            ) {

                return result.next();
            }

        } catch (Exception e) {

            e.printStackTrace();

            // Safer to block the operation if
            // duplicate checking fails.
            return true;
        }
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
    // EMPTY STRING -> NULL
    // =====================================================
    private String emptyToNull(
            String value) {

        if (value == null
                || value.trim().isEmpty()) {

            return null;
        }

        return value.trim();
    }


    // =====================================================
    // RESULTSET -> CATEGORY
    // =====================================================
    private Category createCategoryFromResult(
            ResultSet result) throws Exception {

        return new Category(
                result.getInt("category_id"),
                result.getString("category_name"),
                result.getString("description"),
                result.getString("status")
        );
    }
}