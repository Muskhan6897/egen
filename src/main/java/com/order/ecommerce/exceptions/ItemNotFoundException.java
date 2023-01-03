package com.order.ecommerce.exceptions;

public class ItemNotFoundException extends RuntimeException{
  public ItemNotFoundException(String message) {
    super(message);
  }
}
