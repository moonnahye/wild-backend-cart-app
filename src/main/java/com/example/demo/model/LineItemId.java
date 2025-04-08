package com.example.demo.model;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record LineItemId(
     UUID id
) {

    public static LineItemId generate() {
        return new LineItemId(UUID.randomUUID());
    }
}
