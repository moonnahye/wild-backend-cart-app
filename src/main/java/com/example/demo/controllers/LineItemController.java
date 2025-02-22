package com.example.demo.controllers;

import com.example.demo.application.CartService;
import com.example.demo.controllers.dtos.AddProductToCartDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart/line-items")
public class LineItemController {

    private final CartService cartService;

    public LineItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(
           @Valid @RequestBody AddProductToCartDto addProductToCartDto
    ) {
        cartService.addProduct(
                addProductToCartDto.productId(),
                addProductToCartDto.quantity()
        );
    }
}
