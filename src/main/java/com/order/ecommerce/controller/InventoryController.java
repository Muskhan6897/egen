package com.order.ecommerce.controller;

import com.order.ecommerce.entity.ProductInventory;
import com.order.ecommerce.service.IProductInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.order.ecommerce.utils.EcommerceUtils.validateArgument;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController {

  private final IProductInventoryService inventoryService;

  /**
   * Updates inventory stock
   *
   * @param productId
   * @param quantity
   */
  @PutMapping("/{productId}")
  @Operation(summary = "Updates inventory stock", description = "Update inventory stock")
  public void updateInventoryStock(@PathVariable("productId") String productId,
                                   @RequestParam(name = "quantity") Integer quantity) {
    validateArgument(Objects.isNull(productId) || productId.isEmpty(), "product id cannot be null or empty");
    validateArgument(Objects.isNull(quantity), "quantity cannot be null");
    inventoryService.updateProductInventory(productId, quantity);
  }

  /**
   * List inventory stock
   *
   * @return List<ProductInventory>
   */
  @GetMapping
  @Operation(summary = "Get all inventory stock", description = "Get all order status")
  public List<ProductInventory> findAllInventory() {
    return inventoryService.findAll();
  }
}
