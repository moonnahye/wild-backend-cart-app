package com.example.demo.controllers;

import com.example.demo.application.CartService;
import com.example.demo.controllers.dto.LineItemRequestDto;
import com.example.demo.model.LineItemId;
import com.example.demo.model.ProductId;
import com.example.demo.model.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts/{cartId}/line-items")
@RequiredArgsConstructor
public class LineItemController {

    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable Long cartId, @RequestBody LineItemRequestDto requestDto) {

        cartService.addItemToCart(
                cartId,
                new ProductId(requestDto.getProductId()),
                new ProductOption(requestDto.getColor(), requestDto.getSize()),
                requestDto.getQuantity());

    }

    @DeleteMapping("/{lineItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLineItem(@PathVariable Long cartId, @PathVariable String lineItemId) {
        cartService.removeItemFromCart(
                cartId, new LineItemId(lineItemId));
    }

}
