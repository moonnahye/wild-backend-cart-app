package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProductId (
        String id
){
}
