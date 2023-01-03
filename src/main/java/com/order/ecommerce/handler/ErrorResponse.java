package com.order.ecommerce.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {

  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();
  private List<Error> errors;

  public ErrorResponse(List<Error> errors) {
    this.errors = errors;
  }
}
