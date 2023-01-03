package com.order.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ecommerce_product_inventory")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInventory {

  @Id
  @Column(name = "product_id")
  private String productId;

  @OneToOne
  @MapsId
  @JoinColumn(referencedColumnName = "product_id", name = "product_id")
  private Product product;

  @Column(name = "quantity", nullable = false)
  private int quantity;
}
