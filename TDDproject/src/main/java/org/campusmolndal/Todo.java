package org.campusmolndal;


import org.bson.Document;
import org.bson.types.ObjectId;

public class Todo {

    private String id;
    private String text;
    private boolean done;

    public Todo(String text) {
        this.text = text;
        done = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public static Todo fromDoc(Document document) {
        Todo todo = new Todo(document.getString("text"));
        todo.setId(document.getObjectId("_id").toString());
        todo.setDone(document.getBoolean("done"));
        return todo;
    }
    public Document toDoc() {
        Document document = new Document();
        document.append("_id", id).append("text", text).append("done", done);
        return document;
    }
    @Override
    public String toString() {
        return "ID: " + id
                +"\nText: " + text
                +"\nDone: " + done;
    }
}

