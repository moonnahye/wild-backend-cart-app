package com.example.demo.controllers;

import com.example.demo.application.CartService;
import com.example.demo.controllers.dto.CartResponseDto;
import com.example.demo.controllers.dto.LineItemResponseDto;
import com.example.demo.model.Cart;
import com.example.demo.util.AuthorizationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.util.AuthorizationUtils.extractUserIdFromAuthorization;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(
            @RequestHeader(name = "Authorization", required = false)
            String authorization) {

        String userId = extractUserIdFromAuthorization(authorization);
        Cart cart = cartService.getCart(userId);

        CartResponseDto responseDto = new CartResponseDto(
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

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @DeleteMapping()
    public ResponseEntity<Void> deleteCart(
            @RequestHeader(name = "Authorization", required = false)
            String authorization) {

        String userId = extractUserIdFromAuthorization(authorization);

        cartService.clearCart(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
