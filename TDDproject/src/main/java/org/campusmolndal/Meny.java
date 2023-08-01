package org.campusmolndal;

import java.util.ArrayList;
import java.util.Scanner;

public class Meny {

    private MongoDB mongoDB;
    private Scanner scanner;
    public Meny() {
        mongoDB = new MongoDB("mongodb://localhost:27017", "Todo", "Todo");
        scanner = new Scanner(System.in);
    }
    private String welcome() {
        System.out.println("1. Create" +
                "\n2. Read" +
                "\n3. Update Done" +
                "\n4. Update Text" +
                "\n5. List" +
                "\n6. Delete" +
                "\n> ");
        return scanner.nextLine();
    }
    private void handleUserInput() {
        switch (welcome()) {
            case "1" -> createTodo();
            case "2" -> readTodo();
            case "3" -> updateTodoDone();
            case "4" -> updateTodoText();
            case "5" -> listAllTodos();
            case "6" -> deleteTodo();
            default -> {
                System.out.println("Ogiltligt val. Försök igen.\n");
                handleUserInput();
            }
        }
    }

    private void deleteTodo() {
        System.out.println("Skriv in objectID på den Todo du vill se: ");
        try {
            mongoDB.deleteTodoById(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Todo finns inte.");
        } handleUserInput();
    }

    private void createTodo() {
        System.out.println("Beskriv Todo: ");
        Todo todo = new Todo(scanner.nextLine());
        mongoDB.createTodo(todo);
        handleUserInput();
    }
    private void readTodo() {
        System.out.println("Skriv in objectID på den Todo du vill se: ");
        try {
            Todo todo = mongoDB.getTodoById(scanner.nextLine());
            if(todo != null) {
                System.out.println(todo.toString());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Todo finns inte.");
        } handleUserInput();
    }
    private void updateTodoText() {
        System.out.println("Ange objectID på Todo du vill uppdatera: ");
        String objectID = scanner.nextLine();
        System.out.println("Ange ny beskrivning: ");
        String updatedText = scanner.nextLine();
        try {
            mongoDB.updateTodoText(objectID, updatedText);
        } catch (IllegalArgumentException e) {
            System.out.println("Todo finns inte.");
        } handleUserInput();
    }
    private void updateTodoDone() {
        System.out.println("Ange objectID på Todo du vill uppdatera: ");
        try {
            mongoDB.markTodoAsDone(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Todo finns inte.");
        } handleUserInput();
    }
    private void listAllTodos() {
        ArrayList<Todo> todos = mongoDB.getAllTodos();
        System.out.println("Alla todos:\n");
        for(Todo todo: todos) {
            System.out.println(todo.toString());
        } handleUserInput();
    }
}

