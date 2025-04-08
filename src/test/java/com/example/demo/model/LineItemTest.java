package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class LineItemTest {

    private ProductId productId;
    private ProductOption productOption;

    @BeforeEach
    void setUp() {
        productId = new ProductId("product-1");
        productOption = new ProductOption("red", "M");
    }

    @Test
    @DisplayName("수량을 증가시키면 현재 수량에 더해진다.")
    void addQuantity() {

        int oldQuantity = 2;
        LineItem lineItem = new LineItem(productId, productOption, oldQuantity);
        int newQuantity = 3;

        lineItem.addQuantity(newQuantity);

        assertThat(lineItem.getQuantity()).isEqualTo(oldQuantity + newQuantity);
    }

    @Test
    @DisplayName("상품ID와 옵션이 같으면 같은 라인아이템이다.")
    void whenSameProductIdAndProductOption() {
        LineItem lineItem = new LineItem(productId, productOption, 1);

        boolean result = lineItem.isSameProduct(new ProductId("product-1"), new ProductOption("red", "M"));

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상품ID는 같지만 옵션이 다르면 다른 라인아이템이다.")
    void whenSameProductIdAndDiffrentProductOption() {
        LineItem lineItem = new LineItem(productId, productOption, 1);

        boolean result = lineItem.isSameProduct(new ProductId("product-1"), new ProductOption("black", "L"));

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상품ID가 다르면 다른 라인아이템이다.")
    void whenDifferentProductId() {
        LineItem lineItem = new LineItem(productId, productOption, 1);

        boolean result = lineItem.isSameProduct(new ProductId("product-2"), new ProductOption("red", "M"));

        assertThat(result).isFalse();
    }
}
