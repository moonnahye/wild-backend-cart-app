package com.example.demo.controllers;

import com.example.demo.application.CartService;
import com.example.demo.controllers.dto.LineItemRequestDto;
import com.example.demo.model.LineItemId;
import com.example.demo.model.ProductId;
import com.example.demo.model.ProductOption;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cart/line-items")
public class LineItemController {

    public LineItemController(CartService cartService) {
        this.cartService = cartService;
    }

    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @Valid @RequestBody LineItemRequestDto requestDto) {

        cartService.addItemToCart(
                new ProductId(requestDto.productId()),
                new ProductOption(requestDto.color(), requestDto.size()),
                requestDto.quantity());

    }

    @DeleteMapping("/{lineItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLineItem(@PathVariable String lineItemId) {

        cartService.removeLineItem(
                new LineItemId(lineItemId));
    }

}
