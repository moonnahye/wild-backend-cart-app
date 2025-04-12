package com.example.demo.exception;

import com.example.demo.model.ProductId;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(ProductId productId) {
        super("상품을 찾을 수 없습니다. ID: " + productId.id());
    }
}
