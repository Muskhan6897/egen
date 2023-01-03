package com.order.ecommerce.controller;

import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.enums.OrderStatus;
import com.order.ecommerce.service.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

import static com.order.ecommerce.utils.EcommerceUtils.validateArgument;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final IOrderService orderService;

  /**
   * Creates order
   *
   * @param orderDto
   * @return OrderResponseDto
   */
  @PostMapping
  @Operation(summary = "Create an order", description = "Create an order")
  public OrderResponseDto createOrder(@RequestBody @Valid OrderDto orderDto) {
    return orderService.createOrder(orderDto);
  }

  /**
   * Finds Order by Id
   *
   * @param orderId
   * @return OrderDto
   */
  @GetMapping("/{orderId}")
  @Operation(summary = "Find order", description = "Find order by id")
  public OrderDto findOrderBy(@PathVariable(name = "orderId") String orderId) {
    validateArgument(Objects.isNull(orderId) || orderId.isEmpty(), "order id cannot be null or empty");
    return orderService.findOrderById(orderId);
  }

  /**
   * Updates order status
   *
   * @param orderId
   * @param orderStatus
   */
  @PatchMapping("/{orderId}")
  @Operation(summary = "Update order status", description = "Update order status")
  public void updateOrderStatus(@PathVariable("orderId") String orderId,
                                @RequestParam(name = "orderStatus") OrderStatus orderStatus) {
    validateArgument(Objects.isNull(orderId) || orderId.isEmpty(), "order id cannot be null or empty");
    validateArgument(Objects.isNull(orderStatus), "order status cannot be null");
    orderService.updateOrderStatus(orderId, orderStatus);
  }
}
