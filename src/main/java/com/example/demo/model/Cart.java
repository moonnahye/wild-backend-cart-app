package com.example.demo.model;

import java.util.Collections;
import java.util.List;

public class Cart {
    private List<LineItem> lineItems;

    public Cart(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<LineItem> getLineItems() {
        return Collections.unmodifiableList(lineItems);
    }

    public int getTotalPrice() {
        return lineItems.stream()
                .mapToInt(LineItem::getTotalPrice)
                .sum();
    }

}
