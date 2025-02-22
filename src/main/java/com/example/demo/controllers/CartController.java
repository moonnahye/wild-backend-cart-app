package com.example.demo.controllers;

import com.example.demo.application.CartService;
import com.example.demo.controllers.dtos.CartDto;
import com.example.demo.model.Cart;
import com.example.demo.model.LineItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    CartDto detail() {

        Cart cart = cartService.getCart();
        return new CartDto(
                cart.getLineItems().stream()
                        .map(this::mapToDto)
                        .toList(),
                cart.getTotalPrice());
    }

    private CartDto.LineItemDto mapToDto(LineItem lineItem) {
        return new CartDto.LineItemDto(
                lineItem.getId(),
                lineItem.getProductId(),
                lineItem.getProductName(),
                lineItem.getUnitPrice(),
                lineItem.getQuantity(),
                lineItem.getTotalPrice());
    }
}
