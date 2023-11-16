package com.group14.ecommerce;

import lombok.Data;
import java.util.*;

@Data
public class User {
  private String name;
  private String email;
  private String password;
  // private Cart cart = new ArrayList<Product>();
  // private ArrayList<Cart> orderHistory = new ArrayList<Cart>();

  private static ArrayList<User> allUsers = new ArrayList<User>();

  // public void addToCart(Product product) {
  //   cart.add(product);
  // }
  // public void removeFromCart(Product product) {
  //   cart.remove(product);
  // }
  // public void checkout() {
  //   orderHistory.add(cart);
  //   cart = new ArrayList<Product>();
  // }
}