package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import com.Nkosopa.NMarket.Repository.Customer.ShoppingCartRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartJpaRepository extends JpaRepository<ShoppingCart, Long>, ShoppingCartRepository {
}
