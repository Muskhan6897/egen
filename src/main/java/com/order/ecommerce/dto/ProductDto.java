package com.order.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
public class ProductDto {

  private final String productId;

  @NotBlank(message = "Product Sku cannot be null")
  private final String sku;

  @NotNull(message = "Product Title cannot be null")
  private final String title;

  @NotNull(message = "Product Description cannot be null")
  private final String description;

  @PositiveOrZero(message = "Product Price cannot be negative")
  private final double price;
}
