package com.example.demo.controllers.dto;

import java.util.List;

public record CartResponseDto(
        Long cartId,
        int totalQuantity,
        List<LineItemResponseDto> lineItems
) {
}
