package com.example.demo.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class LineItemTest {

    @Test
    void addQuantity() {

        String productId = "productId-1";
        String productOption = "productOption-1";
        int quantity = 5;
        int delta = 5;
        LineItem lineItem = new LineItem(productId, productOption, quantity);

        lineItem.addQuantity(delta);

        assertThat(lineItem.getQuantity()).isEqualTo(quantity + delta);

    }

}
