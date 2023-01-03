package com.order.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OrderECommerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderECommerceApplication.class, args);
  }

}