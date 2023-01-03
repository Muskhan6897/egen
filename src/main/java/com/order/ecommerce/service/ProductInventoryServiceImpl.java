package com.order.ecommerce.service;

import com.order.ecommerce.entity.Product;
import com.order.ecommerce.entity.ProductInventory;
import com.order.ecommerce.repository.IProductInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInventoryServiceImpl implements IProductInventoryService {

  private final IProductInventoryRepository inventoryRepository;
  private final ProductService productService;

  @Override
  @Transactional
  public void updateProductInventory(String productId, Integer quantity) {
    Product product = productService.findProductByProductId(productId);
    ProductInventory updatedProduct = ProductInventory.builder()
        .product(product)
        .quantity(quantity).build();

    if (inventoryRepository.existsById(productId)) {
      inventoryRepository.updateQuantityUsingProductId(updatedProduct.getQuantity(), productId);
    } else {
      inventoryRepository.save(updatedProduct);
    }
    log.info("Successfully updated quantity to = {} for product id = {}", updatedProduct.getQuantity(), productId);
  }

  @Override
  public List<ProductInventory> findAllById(List<String> ids) {
    log.info("Finding product inventory for ids = {}", ids);
    List<ProductInventory> productInventoryList = (List<ProductInventory>) inventoryRepository.findAllById(ids);
    if (productInventoryList.isEmpty()) {
      log.info("No product(s) found for ids = {}", ids);
      return null;
    }
    log.info("Successfully found {} products", productInventoryList.size());
    return productInventoryList;
  }

  @Override
  public List<ProductInventory> findAll() {
    return (List<ProductInventory>) inventoryRepository.findAll();
  }
}
