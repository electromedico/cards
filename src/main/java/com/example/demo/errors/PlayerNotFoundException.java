/* (C) 2022 */
package com.example.demo.errors;

public class PlayerNotFoundException extends NotFoundException {

  public PlayerNotFoundException(Integer playerId) {
    super("The payer was not found : " + playerId);
  }
}
