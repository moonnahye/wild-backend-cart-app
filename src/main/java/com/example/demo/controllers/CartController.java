package com.example.demo.controllers;

import com.example.demo.application.CartService;
import com.example.demo.controllers.dto.CartResponseDto;
import com.example.demo.controllers.dto.LineItemResponseDto;
import com.example.demo.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{cartId}")
    public CartResponseDto getCart(@PathVariable Long cartId) {

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


    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
