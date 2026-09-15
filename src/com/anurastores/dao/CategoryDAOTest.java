package com.anurastores.dao;

import java.util.List;

import com.anurastores.model.Category;

public class CategoryDAOTest {

    public static void main(String[] args) {

        CategoryDAO categoryDAO =
                new CategoryDAO();

        List<Category> categories =
                categoryDAO.getAllCategories();

        for (Category category : categories) {

            System.out.println(
                    category.getCategoryId()
                    + " | "
                    + category.getCategoryName()
            );
        }
    }
}