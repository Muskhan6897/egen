package com.order.ecommerce.controller;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static com.order.ecommerce.utils.EcommerceUtils.validateArgument;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

  private final IProductService productService;

  /**
   * Creates a product
   *
   * @param productDto
   * @return
   */
  @PostMapping
  @Operation(summary = "Create a product", description = "Create a product")
  public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
    return productService.createProduct(productDto);
  }

  /**
   * Finds product by id
   *
   * @param productId
   * @return
   */
  @GetMapping("/{productId}")
  @Operation(summary = "Find a product", description = "Find a product by id")
  public ProductDto findProductById(@PathVariable(name = "productId") String productId) {
    validateArgument(productId == null || productId.isEmpty(), "Product Id cannot be null or empty");
    return productService.findProductById(productId);
  }
}