package com.example.demo.erros;

public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(Integer playerId) {
        super("The payer was not found : " + playerId);
    }
}
