package com.order.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
public class AddressDto {

  private final String addressId;

  @NotBlank
  private final String address1;

  @NotNull
  private final String address2;

  @NotBlank
  private final String city;

  @NotBlank
  private final String state;

  @NotBlank
  private final String zip;

  @Email
  private final String email;

  @Pattern(regexp = "^\\d{10}$")
  private final String phone;
}
