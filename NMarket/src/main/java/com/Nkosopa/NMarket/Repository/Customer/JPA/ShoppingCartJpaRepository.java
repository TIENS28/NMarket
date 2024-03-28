package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import com.Nkosopa.NMarket.Repository.Customer.ShoppingCartRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartJpaRepository extends JpaRepository<ShoppingCart, Long>, ShoppingCartRepository {

    Optional<ShoppingCart> findByCustomer(Customer customer);
}
