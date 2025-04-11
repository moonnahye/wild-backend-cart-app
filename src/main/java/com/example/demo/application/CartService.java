package com.example.demo.application;

import com.example.demo.model.Cart;
import com.example.demo.model.LineItemId;
import com.example.demo.model.ProductId;
import com.example.demo.model.ProductOption;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    private final HttpSession session;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, HttpSession session) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.session = session;
    }

    public void addItemToCart(ProductId productId, ProductOption option, int quantity) {
        Cart cart = getCart();
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }
        cart.addProduct(productId, option, quantity);
    }

    public void removeLineItem(LineItemId lineItemId) {
        Cart cart = getCart();
        cart.removeLineItem(lineItemId);
    }

    public void clearCart() {
        Cart cart = getCart();
        cart.clearItems();
    }

    public Cart getCart() {
        Long cartId = (Long) session.getAttribute("CART_ID");

        if (cartId != null) {
            return cartRepository.findById(cartId)
                    .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지 않습니다."));
        }

        Cart cart = new Cart();
        Cart savedCart = cartRepository.save(cart);
        session.setAttribute("CART_ID", savedCart.getId());
        return savedCart;
    }
}
