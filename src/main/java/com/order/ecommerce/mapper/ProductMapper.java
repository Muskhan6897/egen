package com.order.ecommerce.mapper;

import com.order.ecommerce.dto.ProductDto;
import com.order.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(imports = {UUID.class, LocalDate.class})
public interface ProductMapper {

  @Mapping(target = "productId", expression = "java(UUID.randomUUID().toString())")
  @Mapping(target = "orderItems", ignore = true)
  @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
  Product toProductEntity(ProductDto productDto);

  ProductDto toProductDto(Product product);
}
