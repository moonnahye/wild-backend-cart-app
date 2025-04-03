package com.example.demo.application;

import com.example.demo.model.*;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartServiceTest {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartService cartService;

    private Long cartId;
    private ProductId productId;
    private ProductOption option;
    private Product product;

    private Cart cart;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        productRepository = mock(ProductRepository.class);
        cartService = new CartService(cartRepository, productRepository);
        
        cartId = 1L;
        productId = new ProductId("product-1");
        option = new ProductOption("red", "M");
        product = new Product(productId, "Product #1", 5000);

        cart = new Cart(List.of());
    }


    @Test
    @DisplayName("상품을 추가할때, 장바구니가 존재하지 않으면 예외가 발생한다")
    void cartNotFound() {
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->cartService.addItemToCart(cartId, productId, option, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품을 추가할때, 상품이 존재하지 않으면 예외가 발생한다")
    void productNotFound() {
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThatThrownBy(()->cartService.addItemToCart(cartId, productId, option, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("빈 장바구니에 상품을 추가하면, 전체 갯수는 상품의 갯수와 같다.")
    void addProductToCart() {
        //given
        mockCartAndProduct();
        int quantity = 2;

        // when
        cartService.addItemToCart(cartId, productId, option, quantity);

        // then
        assertThat(cart.getLineItems()).hasSize(1);
        assertThat(cart.getTotalQuantity()).isEqualTo(quantity);
    }


    @Test
    @DisplayName("상품이 있는 장바구니에 다른 상품을 추가하면, LineItem이 추가되고 전체 수량은 합쳐진다")
    void addAnotherProductToCart() {
        // given
        int existedQuantity = 2;
        LineItem lineItem = new LineItem(productId, option, existedQuantity);
        cart = new Cart(
                List.of(lineItem)
        );

        mockCartAndProduct();

        ProductId newProductId = new ProductId("product-2");
        Product newProduct = new Product(newProductId, "Product #2", 10000);
        when(productRepository.findById(newProductId)).thenReturn(Optional.of(newProduct));

        int newQuantity = 2;

        // when
        cartService.addItemToCart(cartId, newProductId, option, newQuantity);

        // then
        assertThat(cart.getLineItems()).hasSize(2);
        assertThat(cart.getTotalQuantity()).isEqualTo(existedQuantity + newQuantity);
    }

    @Test
    @DisplayName("장바구니에서 LineItem을 삭제하면 해당 상품을 제거할수있다.")
    void removeItemFromCart() {
        // given
        LineItem lineItem = new LineItem(productId, option, 1);
        cart = new Cart(List.of(lineItem));

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when
        cartService.removeItemFromCart(cartId, lineItem.getId());

        // then
        assertThat(cart.getLineItems()).isEmpty();
        assertThat(cart.getTotalQuantity()).isZero();
    }

    @Test
    @DisplayName("상품 수량이 20개가 초과하면 예외가 발생한다.")
    void throwExceptionWhenTotalQuantityOverLimit() {
        // given
        mockCartAndProduct();
        cartService.addItemToCart(cartId, productId, option, 20);

        // when & then
        assertThatThrownBy(()->
                cartService.addItemToCart(cartId, productId, option, 1)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장바구니를 비우면 모든 상품이 제거가 된다.")
    void clearCart() {
        // given
        LineItem lineItem = new LineItem(productId, option, 1);
        cart = new Cart(
                List.of(lineItem)
        );
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when
        cartService.clearCart(cartId);

        // then
        assertThat(cart.getLineItems()).isEmpty();
        assertThat(cart.getTotalQuantity()).isZero();
    }

    private void mockCartAndProduct() {
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    }
}
