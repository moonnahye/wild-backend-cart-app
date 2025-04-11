package com.example.demo.application;

import com.example.demo.model.Cart;
import com.example.demo.model.LineItemId;
import com.example.demo.model.ProductId;
import com.example.demo.model.ProductOption;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartServiceTest {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private HttpSession session;
    private CartService cartService;
    private Cart cart;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        productRepository = mock(ProductRepository.class);
        session =  mock(HttpSession.class);
        cartService = new CartService(cartRepository, productRepository, session);
        cart = mock(Cart.class);
    }

    @DisplayName("세션에 CART_ID가 존재하면 해당 ID로 장바구니를 조회한다")
    @Test
    void getExistingCartId() {
        // given
        Long existingCartId = 123L;

        when(session.getAttribute("CART_ID")).thenReturn(existingCartId);
        when(cartRepository.findById(existingCartId)).thenReturn(Optional.of(cart));

        // when
        Cart result = cartService.getCart();

        // then
        assertThat(result).isSameAs(cart);
        verify(session).getAttribute("CART_ID");
        verify(cartRepository).findById(existingCartId);
    }

    @DisplayName("세션에 CART_ID가 없으면 새 장바구니를 생성하고 세션에 저장한다 ")
    @Test
    void getNonExistingCartId() {
        // given
        Long newCartId = 456L;

        when(session.getAttribute("CART_ID")).thenReturn(null);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cart.getId()).thenReturn(newCartId);

        // when
        Cart result = cartService.getCart();

        // then
        assertThat(result).isSameAs(cart);
        verify(session).setAttribute("CART_ID", newCartId);
    }

    @DisplayName("상품을 장바구니에 추가한다")
    @Test
    void addItemToCart_AddsProductToCart() {
        // given
        Long cartId = 123L;
        ProductId productId = new ProductId("product-1");
        ProductOption option = new ProductOption("Red", "L");
        int quantity = 2;

        when(session.getAttribute("CART_ID")).thenReturn(cartId);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.existsById(productId)).thenReturn(true);

        // when
        cartService.addItemToCart(productId, option, quantity);

        // then
        verify(cart).addProduct(productId, option, quantity);
    }

    @DisplayName("장바구니를 비울 수 있다.")
    @Test
    void clearCart() {
        // given
        Long cartId = 123L;

        when(session.getAttribute("CART_ID")).thenReturn(cartId);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when
        cartService.clearCart();

        // then
        verify(cart).clearItems();
    }

    @DisplayName("장바구니에서 라인 아이템을 제거한다")
    @Test
    void removeLineItem() {
        // given
        Long cartId = 123L;
        LineItemId lineItemId = new LineItemId("lineItem-1");

        when(session.getAttribute("CART_ID")).thenReturn(cartId);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // when
        cartService.removeLineItem(lineItemId);

        // then
        verify(cart).removeLineItem(lineItemId);
    }

}
