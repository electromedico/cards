package com.example.demo.erros;

public class GameNotFoundException extends Exception{
    public GameNotFoundException(int id) {
        super("The game was not found : " + id);
    }
}
