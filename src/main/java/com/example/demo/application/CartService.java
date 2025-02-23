package com.example.demo.application;

import com.example.demo.infrastructure.LineItemDAO;
import com.example.demo.infrastructure.ProductDAO;
import com.example.demo.model.Cart;
import com.example.demo.model.LineItem;
import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final LineItemDAO lineItemDAO;
    private final ProductDAO productDAO;

    public CartService(LineItemDAO lineItemDAO, ProductDAO productDAO) {
        this.lineItemDAO = lineItemDAO;
        this.productDAO = productDAO;
    }

    public Cart getCart() {
        List<LineItem> lineItems = lineItemDAO.findAll();

        lineItems.forEach(lineItem -> {
            String productId = lineItem.getProductId();
            Product product = productDAO.find(productId);

            lineItem.setProduct(product);
        });

        return new Cart(lineItems);
    }

    public void addProduct(String productId, int quantity) {

        List<LineItem> lineItems = lineItemDAO.findAll();

        LineItem lineItem = lineItems.stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (lineItem == null) {
            lineItem = new LineItem(productId, quantity);
            lineItemDAO.add(lineItem);
            return;
        }

        lineItem.addQuantity(quantity);
        lineItemDAO.update(lineItem);
    }
}
