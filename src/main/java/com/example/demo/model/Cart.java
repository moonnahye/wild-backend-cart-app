package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private List<LineItem> lineItems;
    private int totalQuantity;

    public Cart(List<LineItem> lineItems) {
        this.lineItems = new ArrayList<>(lineItems);
        calculateTotalQuantity();
    }

    public List<LineItem> getLineItems() {
        return Collections.unmodifiableList(lineItems);
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void addProduct(String productId, String productOption, int quantity) {

        checkValidQuantity(quantity);

        LineItem lineItem = findLineItem(productId, productOption);

        if (lineItem != null) {
            lineItem.addQuantity(quantity);
            calculateTotalQuantity();
            return;
        }

        lineItem = new LineItem(productId, productOption, quantity);
        lineItems.add(lineItem);
        calculateTotalQuantity();
    }

    public void checkValidQuantity(int quantity) {
        if (totalQuantity + quantity> 20) {
            throw new IllegalArgumentException("담을수 있는 수량을 초과했습니다.");
        }
    }

    private void calculateTotalQuantity() {
        this.totalQuantity = lineItems.stream()
                .mapToInt(LineItem::getQuantity)
                .sum();
    }


    private LineItem findLineItem(String productId, String productOption) {
        return lineItems.stream()
                .filter(i -> i.getProductId().equals(productId)
                        && i.getProductOption().equals(productOption))
                .findFirst()
                .orElse(null);
    }

}
