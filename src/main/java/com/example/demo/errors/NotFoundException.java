package com.example.demo.errors;

public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
