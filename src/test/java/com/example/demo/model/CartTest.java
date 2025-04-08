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

        assertThat(cart.getTotalQuantity()).isZero();
    }

    @Test
    @DisplayName("빈 장바구니에 물건 추가하면 장바구니의 전체수량은 추가한 수량과 같다.")
    void addProductToEmptyCart() {
        Cart cart = new Cart(List.of());

        int quantity = 1;
        cart.addProduct(product1.getId(), productOption1, quantity);

        assertThat(cart.getTotalQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("장바구니에 이미 있는 물건 추가하면 전체 수량은 이미 있던 수량과 새로 추가하는 수량의 합이다.")
    void addExistingProduct() {

        int oldQuantity = 1;
        int newQuantity = 1;

        Cart cart = new Cart();

        cart.addProduct(product1.getId(), productOption1, oldQuantity);
        cart.addProduct(product1.getId(), productOption1, newQuantity);

        assertThat(cart.getLineItems()).hasSize(1);
        assertThat(cart.getTotalQuantity()).isEqualTo(oldQuantity + newQuantity);
    }

    @Test
    @DisplayName("같은 상품의 옵션이 다른 경우를 추가할때, 장바구니의 전체수량은 이미 있던 수량과 새로 추가하는 수량의 합이다. ")
    void addSameProductAndDifferentOption() {

        int oldQuantity = 1;
        int newQuantity = 1;
        Cart cart = new Cart();

        cart.addProduct(product1.getId(), productOption1, oldQuantity);
        cart.addProduct(product1.getId(), productOption2, newQuantity);

        assertThat(cart.getLineItems()).hasSize(2);
        assertThat(cart.getTotalQuantity()).isEqualTo(oldQuantity + newQuantity);
    }

    @Test
    @DisplayName("상품의 옵션이 모두 다른 경우를 추가할때, 장바구니의 전체수량은 이미 있던 수량과 새로 추가하는 수량의 합이다. ")
    void addDifferentProductAndDifferentOption() {

        int oldQuantity = 1;
        int newQuantity = 1;
        Cart cart = new Cart();

        cart.addProduct(product1.getId(), productOption1, oldQuantity);
        cart.addProduct(product2.getId(), productOption2, newQuantity);

        assertThat(cart.getLineItems()).hasSize(2);
        assertThat(cart.getTotalQuantity()).isEqualTo(oldQuantity + newQuantity);
    }


    @Test
    @DisplayName("전체 장바구니의 수량이 20개가 넘어가면 예외가 발생한다.")
    void totalQuantityCanNotOverLimit() {
        Cart cart = new Cart();

        int newQuantity = 5;

        cart.addProduct(product1.getId(), productOption1, 20);

        assertThatThrownBy(
                () -> cart.addProduct(product1.getId(), productOption1, newQuantity)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장바구니를 비우면 전체수량이 0이 된다.")
    void clearCart() {
        Cart cart = new Cart();
        cart.addProduct(product1.getId(), productOption1, 1);
        cart.addProduct(product2.getId(), productOption2, 2);

        cart.clearItems();

        assertThat(cart.getLineItems()).isEmpty();
        assertThat(cart.getTotalQuantity()).isZero();
    }

    @Test
    @DisplayName("장바구니에서 LineItem을 삭제하면 해당 상품을 제거할수있다.")
    void removeLineItem() {
        Cart cart = new Cart();
        cart.addProduct(product1.getId(), productOption1, 1);
        cart.addProduct(product2.getId(), productOption2, 2);

        LineItem lineItem = cart.getLineItems().get(0);
        cart.removeLineItem(lineItem.getId());

        assertThat(cart.getLineItems()).hasSize(1);
        assertThat(cart.getTotalQuantity()).isEqualTo(2);
    }

}
