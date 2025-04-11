package com.example.demo.controllers.dto;

import java.util.List;

public record CartResponseDto(
        int totalQuantity,
        List<LineItemResponseDto> lineItems
) {
}
