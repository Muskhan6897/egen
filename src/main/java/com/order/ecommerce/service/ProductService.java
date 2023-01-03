package com.order.ecommerce.service;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.Product;
import com.order.ecommerce.exceptions.ItemNotFoundException;
import com.order.ecommerce.mapper.ProductMapper;
import com.order.ecommerce.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

  private final IProductRepository productRepository;
  private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

  @Override
  @Transactional
  public ProductDto createProduct(ProductDto productDto) {
    log.info("Creating Product with sku = {}", productDto.getSku());
    Product entity = productMapper.toProductEntity(productDto);
    Product savedProduct = productRepository.save(entity);
    log.info("Successfully saved product with id = {} on {}", savedProduct.getProductId(), savedProduct.getCreatedAt());
    return productMapper.toProductDto(savedProduct);
  }

  @Override
  public ProductDto findProductById(String productId) {
    return productMapper.toProductDto(findProductByProductId(productId));
  }

  public Product findProductByProductId(String productId) {
    log.info("Finding product for productId = {}", productId);
    Optional<Product> product = productRepository.findById(productId);
    if (product.isEmpty()) {
      log.info("Cannot find product with id = {}", productId);
      throw new ItemNotFoundException("Product not found, productId : " + productId);
    }

    log.info("Successfully found product for productId = {}", productId);
    return product.get();
  }
}
