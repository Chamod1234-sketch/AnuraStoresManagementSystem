package com.anurastores.dao;

import java.util.List;

import com.anurastores.model.Product;

public class ProductDAOTest {

    public static void main(String[] args) {

        ProductDAO productDAO =
                new ProductDAO();

        List<Product> products =
                productDAO.getAllProducts();

        for (Product product : products) {

            System.out.println(
                    product.getProductId()
                    + " | "
                    + product.getProductName()
                    + " | "
                    + product.getCategoryName()
                    + " | "
                    + product.getSellingPrice()
                    + " | "
                    + product.getBarcode()
                    + " | "
                    + product.getStatus()
            );
        }
    }
}