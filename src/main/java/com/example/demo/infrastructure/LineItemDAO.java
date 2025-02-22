package com.example.demo.infrastructure;

import com.example.demo.model.LineItem;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LineItemDAO {

    private final MongoDatabase mongoDatabase;

    public LineItemDAO(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public List<LineItem> findAll() {

        MongoCollection<Document> collection =
                mongoDatabase.getCollection("line_items");

        List<Document> documents = new ArrayList<>();
        collection.find().into(documents);

        return documents.stream().map(this::mapToModel).toList();
    }

    private LineItem mapToModel(Document document) {
        return new LineItem(
                document.getObjectId("_id").toString(),
                document.getString("product_id"),
                document.getInteger("quantity")
        );
    }
}
