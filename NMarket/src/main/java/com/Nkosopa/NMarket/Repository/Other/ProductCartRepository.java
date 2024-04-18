package com.Nkosopa.NMarket.Repository.Other;

import com.Nkosopa.NMarket.Entity.Other.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCartRepository extends JpaRepository<CartProduct, Long> {
}
