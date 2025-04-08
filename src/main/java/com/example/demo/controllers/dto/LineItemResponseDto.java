package com.example.demo.controllers.dto;

public record LineItemResponseDto(
        String productId,
        String color,
        String size,
        int quantity
) {

}
