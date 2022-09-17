package com.example.demo.errors;

public class EmptyDeckException extends Exception {
    public EmptyDeckException() {
        super("the deck is empty");
    }
}
