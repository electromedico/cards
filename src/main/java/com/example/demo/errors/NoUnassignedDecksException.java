/* (C) 2022 */
package com.example.demo.errors;

public class NoUnassignedDecksException extends Exception {
  public NoUnassignedDecksException() {
    super("there are not decks available, create one");
  }
}
