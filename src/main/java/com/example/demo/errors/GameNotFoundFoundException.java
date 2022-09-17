package com.example.demo.errors;

public class GameNotFoundFoundException extends NotFoundException {
    public GameNotFoundFoundException(int id) {
        super("The game was not found : " + id);
    }
}
