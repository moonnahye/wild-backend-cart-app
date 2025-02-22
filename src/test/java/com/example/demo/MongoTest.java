package com.example.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MongoTest {

    @Test
    void test() {

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("demo");
        MongoCollection<Document> collection = database.getCollection("products");

        List<Document> documents = new ArrayList<>();
        collection.find().into(documents);

        assertThat(documents.get(0).getString("name")).isEqualTo("티셔츠");
        assertThat(documents.get(1).getString("name")).isEqualTo("청바지");
    }
}
