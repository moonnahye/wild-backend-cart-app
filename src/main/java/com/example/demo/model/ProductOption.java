package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProductOption(
        String color,
        String size
) {
}
