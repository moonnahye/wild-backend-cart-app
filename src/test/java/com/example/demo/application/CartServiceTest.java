package com.example.demo.application;

import com.example.demo.infrastructure.CartRepository;
import com.example.demo.infrastructure.LineItemRepository;
import com.example.demo.infrastructure.ProductRepository;
import com.example.demo.model.Cart;
import com.example.demo.model.LineItem;
import com.example.demo.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CartServiceTest {

    private Product product1;

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        productRepository = mock(ProductRepository.class);
        cartService = new CartService(cartRepository, productRepository);

        product1 = new Product("product-1", "product #1", 5000);
        given(productRepository.find(product1.getId())).willReturn(product1);

        Cart cart = new Cart(List.of());
        given(cartRepository.find()).willReturn(cart);
    }

    @Test
    @DisplayName("장바구니가 비어있으면 총가격은 0원")
    void totalPriceIsZero() {

        Cart cart = cartService.getCart();

        assertThat(cart.getTotalPrice()).isEqualTo(0);
    }

    // 상품담기
    @Test
    @DisplayName("비어있는 장바구니에 상품 담기")
    void addProduct(){
        String productId = product1.getId();
        int quantity = 1;

        cartService.addProduct(productId, quantity);

        verify(cartRepository).save(any());
    }
}
