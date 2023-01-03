package com.order.ecommerce.service;

import com.order.ecommerce.entity.ProductInventory;

import java.util.List;

public interface IProductInventoryService {
  void updateProductInventory(String productId, Integer quantity);

  List<ProductInventory> findAllById(List<String> ids);

  List<ProductInventory> findAll();
}
