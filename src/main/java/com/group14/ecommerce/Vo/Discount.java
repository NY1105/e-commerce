package com.group14.ecommerce.Vo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Inheritance
public class Discount {
  @Id
  private long discountCode;
  private double discountPercentOff = 0;
  private double discountAmount = 0;
}