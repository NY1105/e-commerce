package com.group14.ecommerce.Controllers;

import com.group14.ecommerce.Repository.discountRepository;
import com.group14.ecommerce.Vo.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class discountController {
    @Autowired
    private discountRepository discount_repository;
    @PutMapping("/discounts")
    public void addNewDiscount(@RequestBody List<Discount> discounts){
        discount_repository.saveAllAndFlush(discounts);
    }
    @GetMapping("/discounts")
    public List<Discount> getDiscounts(){
        return discount_repository.findAll();
    }
}
