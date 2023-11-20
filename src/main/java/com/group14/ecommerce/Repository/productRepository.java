package com.group14.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.group14.ecommerce.Vo.*;


@Repository
public interface productRepository extends JpaRepository<Product, Long> {

}