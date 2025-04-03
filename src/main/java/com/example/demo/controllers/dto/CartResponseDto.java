package com.example.demo.controllers.dto;

import java.util.List;

public class CartResponseDto {
    private Long cartId;
    private int totalQuantity;
    private List<LineItemResponseDto> lineItems;

    public CartResponseDto(Long cartId, int totalQuantity, List<LineItemResponseDto> lineItems) {
        this.cartId = cartId;
        this.totalQuantity = totalQuantity;
        this.lineItems = lineItems;
    }

    public Long getCartId() {
        return cartId;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public List<LineItemResponseDto> getLineItems() {
        return lineItems;
    }
}
