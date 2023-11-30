package com.group14.ecommerce.Vo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@DiscriminatorValue("membership")
public class MembershipDiscount extends Discount {
    private int membershipTier;

    public MembershipDiscount(double discountPercentOff, double discountAmount, int membershipTier) {
        super(discountPercentOff,discountAmount);
        setMembershipTier(membershipTier);
    }
}
