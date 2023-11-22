package com.group14.ecommerce.Vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.*;

@Data
@Entity
public class User {
  @Id
  private String userId;
  private String userPassword;
  private double totalSpent;
  @OneToMany
  private List<Cart> carts;
}