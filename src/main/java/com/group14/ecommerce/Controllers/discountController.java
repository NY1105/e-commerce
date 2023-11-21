package com.group14.ecommerce.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


import com.group14.ecommerce.Repository.discountRepository;
import com.group14.ecommerce.Vo.Discount;

@RestController
public class discountController {

    @Autowired
    private discountRepository discount_repository;

    @GetMapping("/discount")
    public String test(){
        return("this is a test");
    }

    @GetMapping("/discounts")
    public List<Discount> getAllDiscounts() {
        return discount_repository.findAll();
    }
}