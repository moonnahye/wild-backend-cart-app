package com.example.demo.controllers;

import com.example.demo.controllers.dtos.CartDto;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final MongoDatabase mongoDatabase;

    public CartController(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @GetMapping
    CartDto detail() {

        MongoCollection<Document> collection =
                mongoDatabase.getCollection("line_items");

        List<Document> documents = new ArrayList<>();
        collection.find().into(documents);

        List<CartDto.LineItemDto> lineItems = documents.stream()
                .map(this::mapToDto)
                .toList();

        int totalPrice = lineItems.stream()
                .mapToInt(CartDto.LineItemDto::totalPrice)
                .sum();

        return new CartDto(
                lineItems, totalPrice);
    }

    private CartDto.LineItemDto mapToDto(Document document) {
        String productId = document.getString("product_id");
        Document productDocument = findProduct(productId);

        int unitPrice = productDocument.getInteger("price");
        int quantity = document.getInteger("quantity");
        return new CartDto.LineItemDto(
                document.getObjectId("_id").toString(),
                document.getString("product_id"),
                productDocument.getString("name"),
                unitPrice,
                quantity,
                unitPrice * quantity);
    }

    private Document findProduct(String productId) {
        MongoCollection<Document> productsCollection =
                mongoDatabase.getCollection("products");

        return productsCollection.find(
                Filters.eq("_id", new ObjectId(productId))
        ).first();
    }
}
