package edu.upc.dsa.exceptions;

public class RepeatedUserException extends RuntimeException {
  public RepeatedUserException(String message) {
    super(message);
  }
}
