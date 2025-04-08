package com.example.demo.controllers;

import com.example.demo.application.CartService;
import com.example.demo.controllers.dto.CartResponseDto;
import com.example.demo.controllers.dto.LineItemResponseDto;
import com.example.demo.model.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private Long cartId = 1L;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    private final CartService cartService;

    @GetMapping
    public CartResponseDto getCart() {

        Cart cart = cartService.getCart(cartId);

        return new CartResponseDto(
                cartId,
                cart.getTotalQuantity(),
                cart.getLineItems().stream()
                        .map(lineItem -> new LineItemResponseDto(
                                        lineItem.getProductId().id(),
                                        lineItem.getProductOption().color(),
                                        lineItem.getProductOption().size(),
                                        lineItem.getQuantity()
                                )
                        ).toList()
        );
    }


    @DeleteMapping()
    public ResponseEntity<Void> deleteCart() {
        cartService.clearCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
