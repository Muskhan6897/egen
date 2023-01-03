package com.order.ecommerce.dto;

import com.order.ecommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class OrderResponseDto {

  @NotNull
  private final String orderId;

  @NotNull
  private final OrderStatus orderStatus;

}
