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

  @NotBlank(message = "address1 cannot be null or empty")
  private final String address1;

  @NotNull(message = "address2 cannot be null")
  private final String address2;

  @NotBlank(message = "city cannot be null or empty")
  private final String city;

  @NotBlank(message = "state cannot be null or empty")
  private final String state;

  @NotBlank(message = "zip cannot be null or empty")
  private final String zip;

  @Email(message = "invalid email entered")
  private final String email;

  @Pattern(regexp = "^\\d{10}$", message = "invalid mobile number entered")
  private final String phone;
}
