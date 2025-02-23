package com.example.demo.infrastructure;

import com.example.demo.model.Cart;
import com.example.demo.model.LineItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartRepository {

    private final LineItemRepository lineItemRepository;

    public CartRepository(LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }

    public Cart find() {
        List<LineItem> lineItems = lineItemRepository.findAll();
        return new Cart(lineItems);
    }

    public void save(Cart cart) {
        cart.getLineItems().forEach(lineItem -> {
            if (lineItem.getId() == null) {
                lineItemRepository.add(lineItem);
            }
            lineItemRepository.update(lineItem);
        });
    }
}
