package com.group14.ecommerce.Vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.*;

@Data
@Entity
public class User {
  @Id
  private String email;
  private String password;
  private double totalSpent;
}