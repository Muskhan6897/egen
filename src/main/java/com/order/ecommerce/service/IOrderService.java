package com.order.ecommerce.service;

import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderItemDto;
import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.enums.OrderStatus;

import java.util.List;

public interface IOrderService {
  OrderResponseDto createOrder(OrderDto orderDto);

  OrderDto findOrderById(String id);

  void updateOrderStatus(String id, OrderStatus status);

  List<OrderItemDto> findByCustomerId(String customerId);
}
