package com.group14.ecommerce.Vo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Discount {
  @Id
  private long discount_code;
  private double discountPercentOff = 0;
  private double discountAmount = 0;
}