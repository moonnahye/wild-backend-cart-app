package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineItem> lineItems = new ArrayList<>();

    private int totalQuantity;

    public Cart() {
    }

    public Cart(List<LineItem> lineItems) {
        this.lineItems = new ArrayList<>(lineItems);
        updateTotalQuantity();
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
            lineItem.addQuantity(quantity);
            updateTotalQuantity();
            return;
        }

        lineItem = new LineItem(productId, productOption, quantity);
        lineItems.add(lineItem);
        updateTotalQuantity();
    }


    private void updateTotalQuantity() {
        this.totalQuantity = lineItems.stream()
                .mapToInt(LineItem::getQuantity)
                .sum();
        if (totalQuantity > 20) {
            throw new IllegalArgumentException("담을수 있는 수량을 초과했습니다.");
        }
    }


    private LineItem findLineItem(ProductId productId, ProductOption productOption) {
        return lineItems.stream()
                .filter(lineItem ->
                        lineItem.isSameProduct(productId,productOption))
                .findFirst()
                .orElse(null);
    }

    public void clearItems() {
        lineItems.clear();
        updateTotalQuantity();
    }

    public void removeLineItem(LineItemId lineItemId) {
        lineItems.removeIf(lineItem ->lineItem.getId().equals(lineItemId));
        updateTotalQuantity();
    }
}
