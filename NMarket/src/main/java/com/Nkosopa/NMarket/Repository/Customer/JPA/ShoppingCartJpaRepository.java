package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import com.Nkosopa.NMarket.Repository.Customer.ShoppingCartRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShoppingCartJpaRepository extends JpaRepository<ShoppingCart, Long>, ShoppingCartRepository {

    @Query(value = "select sc from ShoppingCart sc " +
            "Left join Customer c ON c.id = sc.customer.id " +
            "where sc.customer.id = :customerId")
    Optional<ShoppingCart> findByCustomerId(Long customerId);
}
