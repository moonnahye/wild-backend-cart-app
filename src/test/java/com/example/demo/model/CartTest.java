package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CartTest {

    Product product1;
    Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product("product-1", "Product #1", 5000);
        product2 = new Product("product-2", "Product #2", 3000);
    }

    @Test
    @DisplayName("빈 카트의 총합은 0이다.")
    void getTotalPriceIsZero() {
        Cart cart = new Cart(List.of());

        assertThat(cart.getTotalPrice()).isEqualTo(0);
    }

    @Test
    @DisplayName("장바구니에 있는 하나의 상품의 가격을 모두 더해서 총 가격을 구한다.")
    void getTotalPrice() {

        int quantity = 1;

        Cart cart = new Cart(List.of(
                createLineItem(product1, quantity)
        ));

        assertThat(cart.getTotalPrice()).
                isEqualTo(product1.getPrice() * quantity);
    }

    @Test
    @DisplayName("장바구니에 있는 여러 상품의 가격을 모두 더해서 총 가격을 구한다.")
    void calculateTotalPrice() {
        int quantity1 = 2;
        int quantity2 = 3;

        Cart cart = new Cart(List.of(
                createLineItem(product1, quantity1),
                createLineItem(product2, quantity2)
        ));

        assertThat(cart.getTotalPrice())
                .isEqualTo(product1.getPrice() * quantity1
                        + product2.getPrice() * quantity2);
    }

    @Test
    @DisplayName("비어있는 장바구니에 상품 담기")
    void addProduct(){

        int quantity = 1;

        Cart cart = new Cart(List.of());

        cart.addProduct(product1, quantity);

        assertThat(cart.getLineItems()).hasSize(1);
    }

    @Test
    @DisplayName("장바구니에 없는 상품 담기")
    void addNewProduct(){
        int quantity = 1;

        Cart cart = new Cart(List.of(
                createLineItem(product2, 2)
        ));

        cart.addProduct(product1, quantity);

        assertThat(cart.getLineItems()).hasSize(2);
        assertThat(cart.getLineItems().get(1).getQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("장바구니에 이미있는 상품 담기")
    void addExistingProduct(){

        int oldQuantity = 1;
        int newQuantity = 2;

        Cart cart = new Cart(List.of(
                createLineItem(product1, oldQuantity)
        ));

        cart.addProduct(product1, newQuantity);

        assertThat(cart.getLineItems()).hasSize(1);

        assertThat(cart.getLineItems().getFirst().getQuantity())
                .isEqualTo(oldQuantity + newQuantity);
    }

    private LineItem createLineItem(Product product, int quantity) {
        LineItem lineItem = new LineItem(product.getId(), quantity);
        lineItem.setProduct(product);
        return lineItem;
    }


}
