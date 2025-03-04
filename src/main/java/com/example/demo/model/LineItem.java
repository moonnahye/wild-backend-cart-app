package com.example.demo.model;


public class LineItem {

    private final LineItemId id;
    private final ProductId productId;
    private final ProductOption productOption;

    private int quantity;


    public LineItem(ProductId productId, ProductOption productOption, int quantity) {
        this.id = LineItemId.generate();
        this.productId = productId;
        this.productOption = productOption;
        this.quantity = quantity;
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
}
