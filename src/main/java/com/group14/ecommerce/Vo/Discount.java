package com.group14.ecommerce.Vo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Inheritance
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long discountCode;
  private double discountPercentOff = 0;
  private double discountAmount = 0;

  public Discount(double discountPercentOff, double discountAmount) {
  }
}