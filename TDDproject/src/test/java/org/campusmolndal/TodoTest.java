package org.campusmolndal;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    //h
    Todo sut;

    @BeforeEach
    void setup() {
        sut = new Todo("Diska");
        sut.setId("123");
    }

    @Test
    void testToString() {
        String expected = "ID: " + "123"
                +"\nText: " + "Diska"
                +"\nDone: " + "false";
        assertEquals(expected, sut.toString());
    }

    @Test
    void testToDocNotNull() {
        Document document = sut.toDoc();
        assertNotNull(document);
    }

    @Test
    void testToDocCorrectValues() {
        Document document = sut.toDoc();
        assertEquals(document.getBoolean("done"), sut.isDone());
        assertEquals(document.getString("_id"), sut.getId());
        assertEquals(document.getString("text"), sut.getText());
    }
    @Test
    void testFromDocNotNull() {
        Document document = sut.toDoc();
        Todo todo = Todo.fromDoc(document);
        assertNotNull(todo.getText());
        // assertNotNull(document.get("_id"));
        //assertNotNull(document.get("done"));
    }
    @Test
    void testFromDocCorrectValues() {
        Document document = sut.toDoc();
        assertEquals(document.getBoolean("done"), sut.isDone());
        assertEquals(document.getString("_id"), sut.getId());
        assertEquals(document.getString("text"), sut.getText());
    }

}