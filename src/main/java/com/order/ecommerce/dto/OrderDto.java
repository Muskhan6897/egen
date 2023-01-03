package com.order.ecommerce.dto;

import com.order.ecommerce.enums.OrderStatus;
import com.order.ecommerce.enums.PaymentMode;
import com.order.ecommerce.enums.ShippingMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderDto {

  @NotBlank(message = "customer id cannot be null or empty")
  private final String customerId;

  @PositiveOrZero
  private final double subTotal;

  @PositiveOrZero
  private final double totalAmt;

  @PositiveOrZero
  private final double tax;

  @PositiveOrZero
  private final double shippingCharges;

  @NotBlank(message = "title cannot be null or empty")
  private final String title;

  @NotNull(message = "shipping mode cannot be null")
  private final ShippingMode shippingMode;

  @PositiveOrZero
  private final double amount;

  @NotNull(message = "payment mode cannot be null")
  private final PaymentMode paymentMode;

  @Valid
  @NotNull(message = "billing address cannot be null")
  private final AddressDto billingAddress;

  @Valid
  @NotNull(message = "shipping address cannot be null")
  private final AddressDto shippingAddress;

  @Valid
  @NotNull(message = "order items cannot be null or empty")
  private final List<OrderItemDto> orderItems;

  private final OrderStatus orderStatus;

  private final boolean isBillingAndShippingAddressSame;
}
