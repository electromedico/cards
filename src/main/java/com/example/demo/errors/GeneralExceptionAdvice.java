/* (C) 2022 */
package com.example.demo.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeneralExceptionAdvice {

  @ResponseBody
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String notFoundExceptionHandler(NotFoundException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(ServerException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  String serverExceptionHandler(ServerException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(RevisionsDontMatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String revisionsDontMatchExceptionHandler(RevisionsDontMatchException ex) {
    return ex.getMessage();
  }
}
