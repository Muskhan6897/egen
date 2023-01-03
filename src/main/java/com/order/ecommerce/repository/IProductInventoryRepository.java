package com.order.ecommerce.repository;

import com.order.ecommerce.entity.ProductInventory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductInventoryRepository extends CrudRepository<ProductInventory, String> {

  @Modifying
  @Query(value = "update ecommerce_product_inventory set quantity = :quantity where product_id = :productId", nativeQuery = true)
  void updateQuantityUsingProductId(@Param("quantity") int quantity, @Param("productId") String productId);
}
