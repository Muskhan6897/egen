package com.order.ecommerce.service;

import com.order.ecommerce.dto.*;
import com.order.ecommerce.entity.*;
import com.order.ecommerce.enums.OrderStatus;
import com.order.ecommerce.enums.PaymentMode;
import com.order.ecommerce.enums.PaymentStatus;
import com.order.ecommerce.exceptions.ItemNotFoundException;
import com.order.ecommerce.mapper.OrderDetailsMapper;
import com.order.ecommerce.repository.IAddressRepository;
import com.order.ecommerce.repository.IOrderItemRepository;
import com.order.ecommerce.repository.IOrderRepository;
import com.order.ecommerce.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

  private final IOrderRepository orderRepository;
  private final IOrderItemRepository orderItemRepository;
  private final IPaymentRepository paymentRepository;
  private final IAddressRepository addressRepository;

  private final IProductService productService;
  private final OrderDetailsMapper orderDetailsMapper = Mappers.getMapper(OrderDetailsMapper.class);

  {
    //addressId not present
  }

  private static Payment buildPayment(double amount, PaymentMode paymentMode) {
    return Payment.builder()
        .paymentId(UUID.randomUUID().toString())
        .amount(amount)
        .paymentMode(paymentMode)
        .confirmationNumber(UUID.randomUUID().toString())
        .paymentStatus(PaymentStatus.PROCESSING)
        .createdAt(LocalDate.now())
        .build();
  }

  @Override
  @Transactional
  public OrderResponseDto createOrder(OrderDto orderDto) {
    log.info("Creating Order for customer = {}", orderDto.getCustomerId());

    log.info("Verifying all products exists before generating order");
    List<String> productIds = orderDto.getOrderItems().stream().map(OrderItemDto::getProductId)
        .distinct().collect(Collectors.toList());
    List<ProductDto> products = productService.findAllById(productIds);
    if (products == null || products.isEmpty() || products.size() != productIds.size()) {
      log.info("Not all product(s) exist, failed to create order!");
      return null;
    }

    Order order = generateOrder(orderDto);
    log.info("Generated order for orderId = {}", order.getOrderId());

    Order savedOrder = orderRepository.save(order);

    String savedOrderId = savedOrder.getOrderId();
    List<OrderItem> orderItemList = buildOrderItems(orderDto.getOrderItems(), savedOrderId);
    log.info("Saving order item list for order id = {}", savedOrderId);

    orderItemRepository.saveAll(orderItemList);

    log.info("Successfully saved order & order items with id = {} for customer = {} on {}",
        savedOrder.getOrderId(), savedOrder.getCustomerId(), savedOrder.getCreatedAt());
    return OrderResponseDto.builder()
        .orderId(savedOrderId)
        .orderStatus(savedOrder.getOrderStatus())
        .build();
  }

  @Override
  public OrderDto findOrderById(String orderId) {
    return orderDetailsMapper.toOrderDto(findOrderByOrderId(orderId));
  }

  private Order findOrderByOrderId(String orderId) {
    log.info("Finding order for orderId = {}", orderId);
    Optional<Order> order = orderRepository.findById(orderId);
    if (order.isEmpty()) {
      log.info("Cannot find order with id = {}", orderId);
      throw new ItemNotFoundException("Order not found with orderId : " + orderId);
    }

    log.info("Successfully found order for orderId = {}", orderId);
    return order.get();
  }

  @Override
  public void updateOrderStatus(String orderId, OrderStatus status) {
    Order order = findOrderByOrderId(orderId);
    order.setOrderStatus(status);
    orderRepository.save(order);
    log.info("Successfully updated order status to = {} for order id = {}", status, orderId);
  }

  private Order generateOrder(OrderDto orderDto) {
    Order order = orderDetailsMapper.toOrderEntity(orderDto);

    Payment payment = buildAndSavePayment(orderDto.getAmount(), orderDto.getPaymentMode());
    order.setPayment(payment);

    setBillingAndShippingAddress(order, orderDto);

    return order;
  }

  private void setBillingAndShippingAddress(Order order, OrderDto orderDto) {
    if (orderDto.isBillingAndShippingAddressSame()) {
      Address sameAddress = fetchAndLoadAddress(orderDto.getBillingAddress());
      setBillingAndShippingAddress(order, sameAddress, sameAddress);
    } else {
      Address billingAddress = fetchAndLoadAddress(orderDto.getBillingAddress());
      Address shippingAddress = fetchAndLoadAddress(orderDto.getShippingAddress());
      setBillingAndShippingAddress(order, billingAddress, shippingAddress);
    }
  }

  private Address fetchAndLoadAddress(AddressDto addressDto) {
    if (StringUtils.isNotBlank(addressDto.getAddressId())) {
      Optional<Address> address = fetchAddress(addressDto.getAddressId());
      return address.isEmpty() ? buildAndLoadAddress(addressDto) : address.get();
    }
    return buildAndLoadAddress(addressDto);

  }

  private void setBillingAndShippingAddress(Order order, Address billingAddress, Address shippingAddress) {
    order.setBillingAddress(billingAddress);
    order.setShippingAddress(shippingAddress);
  }

  private Optional<Address> fetchAddress(String addressId) {
    return addressRepository.findById(addressId);
  }

  private List<OrderItem> buildOrderItems(List<OrderItemDto> orderItemsDtoList, String orderId) {
    return orderItemsDtoList
        .stream()
        .map(orderItemDto -> new OrderItem(new OrderItemPk(orderItemDto.getProductId(), orderId), null, null, orderItemDto.getQuantity()))
        .collect(Collectors.toList());
  }

  private Payment buildAndSavePayment(double amount, PaymentMode paymentMode) {
    Payment payment = buildPayment(amount, paymentMode);
    log.info("Saving payment details for payment id = {}", payment.getPaymentId());
    return paymentRepository.save(payment);
  }

  private Address buildAndLoadAddress(AddressDto addressDto) {
    Address addressEntity = orderDetailsMapper.toAddressEntity(addressDto);
    log.info("Saving billing/shipping address for address id = {}", addressEntity.getAddressId());
    return addressRepository.save(addressEntity);
  }
}
