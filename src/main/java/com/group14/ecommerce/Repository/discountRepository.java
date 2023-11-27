package com.group14.ecommerce.Repository;

import com.group14.ecommerce.Vo.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface discountRepository extends JpaRepository<Discount, Long> {
    @Query("SELECT d FROM CartDiscount d WHERE d.itemsInCart = :count")
    Optional<Discount> findByCount(@Param("count") int count);

    @Query("SELECT d FROM MembershipDiscount d WHERE  d.membershipTier = :tier")
    Optional<Discount> findByTier(@Param("tier") int tier);
}