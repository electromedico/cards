/* (C) 2022 */
package com.example.demo.errors;

public class ServerException extends RuntimeException {
  public ServerException(Throwable cause) {
    super("There was an Server error", cause);
  }
}
