package com.group14.ecommerce.Controllers;

import com.group14.ecommerce.Repository.discountRepository;
import com.group14.ecommerce.Vo.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class discountController {
    @Autowired
    private discountRepository discount_repository;
    @PostMapping("/discounts")
    public void addNewDiscount(@RequestBody List<Discount> discounts){
        discount_repository.saveAllAndFlush(discounts);
    }
}
