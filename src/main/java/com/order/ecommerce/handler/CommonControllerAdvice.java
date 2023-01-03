package com.order.ecommerce.handler;

import com.order.ecommerce.exceptions.ItemNotFoundException;
import com.order.ecommerce.exceptions.NotEnoughQuantityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CommonControllerAdvice {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error("Validation failure {}", e.getMessage());
    ErrorResponse response = ErrorResponse.builder().errors(e.getFieldErrors().stream()
        .map(error -> new Error("OEC-1001", String.format("%s %s", error.getField(), error.getDefaultMessage())))
        .collect(Collectors.toList())).build();
    return new ResponseEntity(Optional.of(response), HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ItemNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException ex) {
    log.error("Error occurred {}", ex.getMessage());
    ErrorResponse response = ErrorResponse.builder().errors(List.of(new Error("OEC-1002", ex.getMessage()))).build();

    return new ResponseEntity(Optional.of(response), HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(NotEnoughQuantityException.class)
  public ResponseEntity<ErrorResponse> handleQuantityNotEnoughException(NotEnoughQuantityException ex) {
    log.error("Error occurred {}", ex.getMessage());
    ErrorResponse response = ErrorResponse.builder().errors(List.of(new Error("OEC-1003", ex.getMessage()))).build();

    return new ResponseEntity(Optional.of(response), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleAnyRuntimeException(RuntimeException ex) {
    log.error("Error occurred {}", ex);
    ErrorResponse response = ErrorResponse.builder().errors(List.of(new Error("OEC-1004", ex.getMessage()))).build();

    return new ResponseEntity(Optional.of(response), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
