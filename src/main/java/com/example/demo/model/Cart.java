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

    public void addProduct(ProductId productId, ProductOption productOption, int quantity) {


        LineItem lineItem = findLineItem(productId, productOption);

        if (lineItem != null) {
            lineItem.addQuantity(quantity, productOption);
            calculateTotalQuantity();
            checkValidQuantity();
            return;
        }

        lineItem = new LineItem(productId, productOption, quantity);
        lineItems.add(lineItem);
        calculateTotalQuantity();
        checkValidQuantity();
    }

    public void checkValidQuantity() {
        if (totalQuantity > 20) {
            throw new IllegalArgumentException("담을수 있는 수량을 초과했습니다.");
        }
    }

    private void calculateTotalQuantity() {
        this.totalQuantity = lineItems.stream()
                .mapToInt(LineItem::getQuantity)
                .sum();
    }


    private LineItem findLineItem(ProductId productId, ProductOption productOption) {
        return lineItems.stream()
                .filter(lineItem ->
                        lineItem.isSameProduct(productId,productOption))
                .findFirst()
                .orElse(null);
    }

}
