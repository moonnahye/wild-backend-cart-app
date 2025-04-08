package com.example.demo.controllers.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LineItemRequestDto(

        @NotBlank(message = "상품 ID는 필수입니다.")
        String productId,

        @NotBlank(message = "색상은 필수입니다.")
        String color,

        @NotBlank(message = "사이즈는 필수입니다.")
        String size,

        @Min(value = 1, message = "최소 수량은 1개입니다.")
        @Max(value = 20, message = "최대 수량은 20개입니다.")
        int quantity
) {
}
