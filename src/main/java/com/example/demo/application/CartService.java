package com.example.demo.application;

import com.example.demo.exception.CartNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.model.LineItemId;
import com.example.demo.model.ProductId;
import com.example.demo.model.ProductOption;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public void addItemToCart(String userId, ProductId productId, ProductOption option, int quantity) {
        Cart cart = getCart(userId);
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }
        cart.addProduct(productId, option, quantity);
    }

    public void removeLineItem(String userId, LineItemId lineItemId) {
        Cart cart = getCart(userId);
        cart.removeLineItem(lineItemId);
    }

    public void clearCart(String userId) {
        Cart cart = getCart(userId);
        cart.clearItems();
    }

    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(CartNotFoundException::new);
    }
}
