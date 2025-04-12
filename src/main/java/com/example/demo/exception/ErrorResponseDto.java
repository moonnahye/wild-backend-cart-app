package com.example.demo.exception;


public record ErrorResponseDto(
        String code,
        String message
) {
}
