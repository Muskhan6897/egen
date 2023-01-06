package com.order.ecommerce.repository;

import com.order.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends CrudRepository<Order, String> {

  @Query(value = "select * from ecommerce_order i where i.customer_id = :customer_id", nativeQuery = true)
  List<Order> findByCustomerId(@Param("customer_id")String customerId);
}
