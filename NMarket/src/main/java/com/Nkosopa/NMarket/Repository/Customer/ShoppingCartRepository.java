package com.Nkosopa.NMarket.Repository.Customer;

import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
