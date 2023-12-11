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
  private double discountPercentOff;
  private double discountAmount;
  
  public Discount(double discountPercentOff, double discountAmount) {
  }
}