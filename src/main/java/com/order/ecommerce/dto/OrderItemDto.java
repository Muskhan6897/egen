package com.order.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
public class OrderItemDto {

  @NotNull(message = "product id cannot be null")
  private final String productId;

  @Positive(message = "quantity cannot be negative")
  private final int quantity;
}
