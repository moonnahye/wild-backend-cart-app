package com.example.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCartNotFound(CartNotFoundException e) {
        ErrorResponseDto responseDto = new ErrorResponseDto("CART_NOT_FOUND", e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotFound(ProductNotFoundException e) {
        ErrorResponseDto responseDto = new ErrorResponseDto("PRODUCT_NOT_FOUND", e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartQuantityLimitException.class)
    public ResponseEntity<ErrorResponseDto> handleCartQuantityLimit(CartQuantityLimitException e) {
        ErrorResponseDto responseDto = new ErrorResponseDto("CART_QUANTITY_LIMIT", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorized(UnauthorizedException e) {
        ErrorResponseDto responseDto = new ErrorResponseDto("UNAUTHORIZED", e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

}
