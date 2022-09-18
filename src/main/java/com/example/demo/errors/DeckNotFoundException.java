/* (C) 2022 */
package com.example.demo.errors;

public class DeckNotFoundException extends NotFoundException {
  public DeckNotFoundException(Integer deckId) {
    super("The deck was not found: " + deckId);
  }
}
