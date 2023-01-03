package com.order.ecommerce.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "ecommerce_address")
public class Address implements Serializable {

  @Id
  @Column(name = "address_id", nullable = false, unique = true)
  private String addressId;

  @Column(name = "address1", nullable = false)
  private String address1;

  @Column(name = "address2", nullable = false)
  private String address2;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "state", nullable = false)
  private String state;

  @Column(name = "zip", nullable = false)
  private String zip;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "phone", nullable = false)
  private String phone;

  @Column(name = "createdAt", nullable = false)
  private LocalDate createdAt;

  @OneToMany(targetEntity = Order.class, fetch = FetchType.LAZY, mappedBy = "billingAddress")
  private List<Order> order;
}
