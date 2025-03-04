package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartTest {

    private Product product1;
    private Product product2;
    private ProductOption productOption1;
    private ProductOption productOption2;

    @BeforeEach
    void setUp() {
        product1 = new Product(new ProductId("product-1"), "product #1", 5000);
        product2 = new Product(new ProductId("product-2"), "product #2", 3000);

        productOption1 = new ProductOption("red", "M");
        productOption2 = new ProductOption("black", "L");
    }

    @Test
    @DisplayName("빈 장바구니의 수량은 0이다.")
    void cartTotalQuantityIsZero() {
        Cart cart = new Cart(List.of());

        assertThat(cart.getTotalQuantity()).isEqualTo(0);
    }

    @Test
    @DisplayName("빈 장바구니에 물건 추가하면 장바구니의 전체수량은 추가한 수량과 같다.")
    void addProduct() {
        Cart cart = new Cart(List.of());

        int quantity = 1;
        cart.addProduct(product1.getId(), productOption1, quantity);

        assertThat(cart.getTotalQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("장바구니에 이미 있는 물건 추가하면 전체 수량은 이미 있던 수량과 새로 추가하는 수량의 합이다.")
    void addExistingProduct() {

        int oldQuantity = 1;
        Cart cart = new Cart(List.of(
                createLineItem(product1, productOption1, oldQuantity)
        ));

        int newQuantity = 1;
        cart.addProduct(product1.getId(), productOption1, newQuantity);

        assertThat(cart.getLineItems()).hasSize(1);
        assertThat(cart.getTotalQuantity()).isEqualTo(oldQuantity + newQuantity);
    }

    @Test
    @DisplayName("장바구니에 새로운 있는 물건 추가하면 전체 수량은 이미 있던 물건의 수량과 " +
            "새로 추가하는 물건의 수량의 합이다.")
    void addNewProduct() {

        int oldQuantity = 1;
        Cart cart = new Cart(List.of(
                createLineItem(product2, productOption2, oldQuantity)
        ));

        int newQuantity = 1;
        cart.addProduct(product1.getId(), productOption1, newQuantity);

        assertThat(cart.getLineItems()).hasSize(2);
        assertThat(cart.getTotalQuantity()).isEqualTo(oldQuantity + newQuantity);
    }

    @Test
    @DisplayName("전체 장바구니의 수량이 20개가 넘어가면 예외가 발생한다.")
    void totalQuantityCanNotOverLimit() {
        Cart cart = new Cart(List.of(
                createLineItem(product1, productOption1, 19)
        ));

        int newQuantity = 5;

        assertThatThrownBy(
                () -> cart.addProduct(product1.getId(), productOption1, newQuantity)
        ).isInstanceOf(IllegalArgumentException.class);

    }



    private LineItem createLineItem(Product product, ProductOption productOption, int quantity) {
        return new LineItem(product.getId(), productOption, quantity);
    }

}
