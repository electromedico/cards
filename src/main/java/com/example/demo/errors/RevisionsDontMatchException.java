/* (C) 2022 */
package com.example.demo.errors;

public class RevisionsDontMatchException extends Exception {
  public RevisionsDontMatchException(int revision, int expected) {
    super("the revisions don't Match " + revision + " expected " + expected);
  }
}
