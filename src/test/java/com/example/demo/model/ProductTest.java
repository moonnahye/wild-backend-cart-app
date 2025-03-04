package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("product-1", "Product #1", 5000);
    }

    @Test
    void changeProductName(){

        String newName = "NewProduct";
        product.changeName(newName);

        assertThat(product.getName()).isEqualTo(newName);
    }

    @Test
    void changeProductPrice(){
        int newPrice = 10000;
        product.changePrice(newPrice);

        assertThat(product.getPrice()).isEqualTo(newPrice);
    }

}
