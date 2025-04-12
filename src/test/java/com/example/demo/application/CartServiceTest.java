package com.example.demo.application;

import com.example.demo.exception.CartNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.model.LineItemId;
import com.example.demo.model.ProductId;
import com.example.demo.model.ProductOption;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartServiceTest {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartService cartService;
    private Cart cart;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        productRepository = mock(ProductRepository.class);
        cartService = new CartService(cartRepository, productRepository);
        cart = mock(Cart.class);
    }

    @DisplayName("userId가 있을때 카트를 찾을수있다.")
    @Test
    void getCart() {
        // given
        String userId = "userA";
        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));

        // when
        Cart result = cartService.getCart(userId);

        // then
        assertThat(result).isSameAs(cart);
        verify(cartRepository).findByUserId(userId);
    }

    @DisplayName("장바구니를 못찾으면 예외가 발생한다.")
    @Test
    void cannotFindCart() {
        String userId = "userA";
        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.getCart(userId))
                .isInstanceOf(CartNotFoundException.class);
    }

    @DisplayName("상품을 장바구니에 추가한다")
    @Test
    void addItemToCart_AddsProductToCart() {
        // given
        String userId = "userA";
        ProductId productId = new ProductId("product-1");
        ProductOption option = new ProductOption("Red", "L");
        int quantity = 2;

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));
        when(productRepository.existsById(productId))
                .thenReturn(true);

        // when
        cartService.addItemToCart(userId, productId, option, quantity);

        // then
        verify(cart).addProduct(productId, option, quantity);
    }

    @DisplayName("장바구니에 넣을 상품이 존재하지 않으면 예외가 발생한다.")
    @Test
    void throwExceptionWhenProductNotFound() {
        String userId = "userA";
        ProductId productId = new ProductId("product-1");
        ProductOption option = new ProductOption("Red", "L");
        int quantity = 2;

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));
        when(productRepository.existsById(productId))
                .thenReturn(false);

        assertThatThrownBy(
                () -> cartService.addItemToCart(userId, productId, option, quantity))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("장바구니를 비울 수 있다.")
    @Test
    void clearCart() {
        // given
        String userId = "userA";

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));

        // when
        cartService.clearCart(userId);

        // then
        verify(cart).clearItems();
    }

    @DisplayName("장바구니에서 라인 아이템을 제거한다")
    @Test
    void removeLineItem() {
        // given
        String userId = "userA";
        LineItemId lineItemId = new LineItemId("lineItem-1");

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));

        // when
        cartService.removeLineItem(userId, lineItemId);

        // then
        verify(cart).removeLineItem(lineItemId);
    }

}
