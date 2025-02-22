package com.example.demo.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AddProductToCartDto(
        @NotBlank
        String productId,
        @Positive
        int quantity
) {
}
