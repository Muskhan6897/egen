package com.order.ecommerce.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@UtilityClass
@Slf4j
public class EcommerceUtils {

  public static void validateArgument(boolean condition, String message) {
    if (condition) {
      log.error("Error while processing request with message = {}", message);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
  }

}
