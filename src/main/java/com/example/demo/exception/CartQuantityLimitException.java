package com.example.demo.exception;

public class CartQuantityLimitException extends RuntimeException {
    public CartQuantityLimitException() {
        super("장바구니에 담을 수 있는 수량을 초과했습니다.");
    }
}
