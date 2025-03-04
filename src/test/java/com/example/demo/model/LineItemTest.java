package com.example.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class LineItemTest {

    @Test
    @DisplayName("같은 상품과 옵션일 경우, 수량을 추가할 수 있어야 한다.")
    void isSameProduct(){
        ProductId productId = new ProductId("productId");
        ProductOption productOption = new ProductOption("red", "M");
        int quantity = 1;
        int delta = 5;

        LineItem lineItem = new LineItem(productId, productOption, quantity);
        lineItem.addQuantity(delta, productOption);

        assertThat(lineItem.isSameProduct(productId, new ProductOption("red", "M"))).isTrue();
        assertThat(lineItem.getQuantity()).isEqualTo(quantity + delta);

    }

    @Test
    @DisplayName("같은 상품이라도 옵션이 다르면, 수량은 추가되지 않는다.")
    void sameProductAndDifferentOption(){
        ProductId productId = new ProductId("productId");
        ProductOption productOption = new ProductOption("red", "M");
        int quantity = 1;
        int delta = 5;
        LineItem lineItem = new LineItem(productId, productOption, quantity);

        ProductOption productOption2 = new ProductOption("black", "M");
        lineItem.addQuantity(delta, productOption2);

        assertThat(lineItem.isSameProduct(productId, productOption2)).isFalse();
        assertThat(lineItem.getQuantity()).isEqualTo(quantity);
    }
}
