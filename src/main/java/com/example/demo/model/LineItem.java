package com.example.demo.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="line_items")
public class LineItem {

    @EmbeddedId
    private LineItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @Embedded
    private ProductId productId;

    @Embedded
    private ProductOption productOption;

    private int quantity;

    protected LineItem() {
    }

    public LineItem(ProductId productId, ProductOption productOption, int quantity) {
        this.id = LineItemId.generate();
        this.productId = productId;
        this.productOption = productOption;
        this.quantity = quantity;
    }

    public LineItemId getId() {
        return id;
    }

    public ProductId getProductId() {
        return productId;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public int getQuantity() {
        return quantity;
    }


    public void addQuantity(int quantity, ProductOption productOption) {
        if(!this.productOption.equals(productOption)) {
            return;
        }
        this.quantity += quantity;
    }

    public boolean isSameProduct(ProductId productId, ProductOption productOption) {
        return this.productId.equals(productId) && this.productOption.equals(productOption);
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
