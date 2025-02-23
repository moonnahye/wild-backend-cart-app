package com.example.demo.infrastructure;

import com.example.demo.model.Product;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductRepository {

    private final MongoDatabase mongoDatabase;

    public ProductRepository(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public Product find(String productId) {

        MongoCollection<Document> collection = mongoDatabase.getCollection("products");

        Document document = collection.find(
                Filters.eq("_id", new ObjectId(productId))
        ).first();

        return new Product(
                document.getObjectId("_id").toString(),
                document.getString("name"),
                document.getInteger("price")
        );
    }

    public List<Product> findAllByIds(List<String> productIds) {
        return productIds.stream()
                .map(this::find)
                .toList();
    }
}
