package com.group14.ecommerce.Repository;

import com.group14.ecommerce.Vo.Cart;
import com.group14.ecommerce.Vo.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface discountRepository extends JpaRepository<Discount, Long> {

}