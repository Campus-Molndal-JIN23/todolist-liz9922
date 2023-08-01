package org.campusmolndal;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDB {
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> todoCollection;
    private final String collectionName;
    private final String databaseName;
    private final String connectionString;

    public MongoDB(String connectionString, String collectionName, String databaseName) {
        this.connectionString = connectionString;
        this.collectionName = collectionName;
        this.databaseName = databaseName;
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        connectToDatabase();
    }
    private void connectToDatabase() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .serverApi(serverApi)
                .build();
        try {
            mongoClient = MongoClients.create(settings);
            mongoDatabase = mongoClient.getDatabase(databaseName);
            todoCollection = mongoDatabase.getCollection(collectionName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to MongoDB.", e);
        }
    }
    public void createTodo(Todo todo) {
        Document document = todo.toDoc();
        if (todoCollection.find(document).first() == null) {
            todoCollection.insertOne(document);
        }
    }

    public Todo getTodoById(String objectId) {
        ObjectId id = new ObjectId(objectId);
        Document document = new Document("_id", id);
        if (todoCollection.find(document).first() != null) {
            return Todo.fromDoc(document);
        }
        throw new IllegalArgumentException();
    }

    public void markTodoAsDone(String objectId) {
        ObjectId id = new ObjectId(objectId);
        Document document = new Document("_id", id);
        if (todoCollection.find(document).first() != null) {
            Document update = new Document("$set", new Document("done", true));
            todoCollection.updateOne(document, update);
            return;
        } throw new IllegalArgumentException();
    }

    public void updateTodoText(String objectId, String updatedText) {
        ObjectId id = new ObjectId(objectId);
        Document document = new Document("_id", id);
        if (todoCollection.find(document).first() != null) {
            Document update = new Document("$set", new Document("text", updatedText));
            todoCollection.updateOne(document, update);
            return;
        } throw new IllegalArgumentException();
    }

    public void deleteTodoById(String objectId) {
        ObjectId id = new ObjectId(objectId);
        Document document = new Document("_id", id);
        if(todoCollection.find(document).first() != null) {
            todoCollection.deleteOne(document);
            return;
        } throw new IllegalArgumentException();

    }

    public ArrayList<Todo> getAllTodos() {
        FindIterable<Document> todos = todoCollection.find();
        ArrayList<Todo> allTodos = new ArrayList<>();
        for (Document doc : todos) {
            allTodos.add(Todo.fromDoc(doc));
        }
        return allTodos;
    }

}
