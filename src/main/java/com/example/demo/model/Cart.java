package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private List<LineItem> lineItems;

    public Cart(List<LineItem> lineItems) {
        this.lineItems = new ArrayList<>(lineItems);
    }

    public List<LineItem> getLineItems() {
        return Collections.unmodifiableList(lineItems);
    }

    public int getTotalPrice() {
        return lineItems.stream()
                .mapToInt(LineItem::getTotalPrice)
                .sum();
    }

    public void addProduct(Product product, int quantity) {
        LineItem lineItem = getLineItem(product);

        if (lineItem != null) {
            lineItem.addQuantity(quantity);
            return;
        }

        lineItem = new LineItem(product.getId(), quantity);
        lineItem.setProduct(product);

        lineItems.add(lineItem);
    }

    public LineItem getLineItem(Product product) {
        return lineItems.stream()
                .filter(i -> i.getProductId().equals(product.getId()))
                .findFirst()
                .orElse(null);
    }
}
