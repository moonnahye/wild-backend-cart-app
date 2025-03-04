package com.example.demo.model;

import java.util.UUID;

public record LineItemId(
     String id
) {

    public static LineItemId generate() {
        return new LineItemId("lineItemId-" + UUID.randomUUID());
    }
}
