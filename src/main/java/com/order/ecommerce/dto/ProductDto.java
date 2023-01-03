package com.order.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class ProductDto {

  private final String productId;

  @NotBlank
  private final String sku;

  @NotNull
  private final String title;

  @NotNull
  private final String description;

  @PositiveOrZero
  private final double price;
}
