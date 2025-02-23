package com.example.demo.application;

import com.example.demo.infrastructure.CartRepository;
import com.example.demo.infrastructure.LineItemRepository;
import com.example.demo.infrastructure.ProductRepository;
import com.example.demo.model.Cart;
import com.example.demo.model.LineItem;
import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getCart() {

        Cart cart = cartRepository.find();

        List<String> productIds = cart.getLineItems().stream()
                .map(LineItem::getProductId)
                .toList();

        Map<String, Product> products = new HashMap<>();
        productRepository.findAllByIds(productIds).forEach(
                product -> products.put(product.getId(), product)
        );

        cart.getLineItems().forEach(lineItem -> {
            Product product = products.get(lineItem.getProductId());
            lineItem.setProduct(product);
        });

        return cart;
    }

    public void addProduct(String productId, int quantity) {

        Cart cart = getCart();
        Product product = productRepository.find(productId);

        cart.addProduct(product, quantity);

        cartRepository.save(cart);
    }
}
