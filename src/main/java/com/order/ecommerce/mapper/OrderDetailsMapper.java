package com.order.ecommerce.mapper;

import com.order.ecommerce.dto.AddressDto;
import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderItemDto;
import com.order.ecommerce.entity.Address;
import com.order.ecommerce.entity.Order;
import com.order.ecommerce.entity.OrderItem;
import com.order.ecommerce.enums.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(imports = {UUID.class, LocalDate.class, OrderStatus.class})
public interface OrderDetailsMapper {

  @Mapping(source = "payment.amount", target = "amount")
  @Mapping(source = "payment.paymentMode", target = "paymentMode")
  @Mapping(target = "isBillingAndShippingAddressSame", ignore = true)
  OrderDto toOrderDto(Order order);

  @Mapping(target = "orderId", expression = "java(UUID.randomUUID().toString())")
  @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
  @Mapping(target = "orderStatus", expression = "java(OrderStatus.PROCESSING)")
  @Mapping(target = "payment", ignore = true)
  Order toOrderEntity(OrderDto orderDto);

  @Mapping(source = "product.productId", target = "productId")
  OrderItemDto toOrderItemDto(OrderItem orderItem);

  @Mapping(target = "orderItemPk", ignore = true)
  @Mapping(target = "product", ignore = true)
  @Mapping(target = "order", ignore = true)
  OrderItem toOrderItemEntity(OrderItemDto orderItemDto);

  @Mapping(target = "addressId", expression = "java(UUID.randomUUID().toString())")
  @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
  @Mapping(target = "order", ignore = true)
  Address toAddressEntity(AddressDto addressDto);

  AddressDto toAddressDto(Address address);
}
