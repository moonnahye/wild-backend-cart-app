package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LineItemTest {

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product("product-1", "Product #1", 5000);
    }

    @Test
    void addQuantity() {

        int quantity = 1;
        int delta = 2;

        LineItem lineItem = new LineItem(product.getId(), quantity);
        lineItem.setProduct(product);

        lineItem.addQuantity(delta);

        assertThat(lineItem.getQuantity()).isEqualTo(quantity + delta);
    }

    @Test
    void setProductAndGetTotalPrice() {

        int quantity = 1;
        LineItem lineItem = new LineItem(product.getId(), quantity);
        lineItem.setProduct(product);

        assertThat(lineItem.getProductName()).isEqualTo(product.getName());
        assertThat(lineItem.getUnitPrice()).isEqualTo(product.getPrice());

        assertThat(lineItem.getTotalPrice()).isEqualTo(quantity * product.getPrice());
    }



}
