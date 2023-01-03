package com.order.ecommerce.exceptions;

public class NotEnoughQuantityException extends RuntimeException {
  public NotEnoughQuantityException(String message) {
    super(message);
  }
}
